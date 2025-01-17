<template>
  <div class="app-container">
    <!-- 添加自习室按钮 -->
    <el-row :gutter="20" class="mb-20">
      <el-col>
        <el-button type="primary" @click="handleAddRoom" style="margin-bottom: 20px;" v-hasPermi="['study:room:add']">新增自习室</el-button>
      </el-col>
    </el-row>

    <!-- 自习室列表 -->
    <el-table :data="studyRoomList" v-loading="loading" border style="width: 100%">
      <el-table-column label="自习室ID" prop="roomId" align="center"></el-table-column>
      <el-table-column label="自习室名称" prop="roomName" align="center"></el-table-column>
      <el-table-column label="容量" prop="capacity" align="center"></el-table-column>
      <el-table-column label="位置" prop="location" align="center"></el-table-column>
      <el-table-column label="状态" align="center">
        <template #default="scope">
          <el-tag :type="scope.row.status === 0 ? 'warning' : 'success'">
            {{ scope.row.status === 1 ? '可用' : '不可用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="280px">
        <template #default="scope">
          <el-button type="success" size="mini" @click="openCommentDialog(scope.row)">评论</el-button>
          <el-button type="info" size="mini" @click="handleView(scope.row)">查看</el-button>
          <el-button type="primary" size="mini" @click="handleEdit(scope.row)" v-hasPermi="['study:room:edit']">编辑</el-button>
          <el-button type="danger" size="mini" @click="handleDelete(scope.row)" v-hasPermi="['study:room:delete']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="fetchStudyRooms"
    />

    <!-- 添加/编辑自习室对话框 -->
    <el-dialog :visible.sync="dialogVisible" :title="dialogTitle" width="30%">
      <el-form :model="roomForm" label-width="100px">
        <el-form-item label="自习室名称">
          <el-input v-model="roomForm.roomName"></el-input>
        </el-form-item>
        <el-form-item label="容量">
          <el-input-number v-model="roomForm.capacity" :min="1" :max="100"></el-input-number>
        </el-form-item>
        <el-form-item label="自习室位置">
          <el-input v-model="roomForm.location"></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="roomForm.status">
            <el-option label="可用" :value="1"></el-option>
            <el-option label="不可用" :value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="自习室描述">
          <el-input type="textarea" v-model="roomForm.description"></el-input>
        </el-form-item>
        <el-form-item label="自习室图片">
          <ImageUpload v-model="roomForm.image"></ImageUpload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">{{ dialogButtonText }}</el-button>
      </div>
    </el-dialog>

    <!-- 查看自习室对话框 -->
    <el-dialog :visible.sync="viewDialogVisible" title="自习室详情" width="40%">
      <el-form :model="viewRoomForm" label-width="100px">
        <el-form-item label="自习室名称">
          <el-input v-model="viewRoomForm.roomName" disabled></el-input>
        </el-form-item>
        <el-form-item label="位置">
          <el-input v-model="viewRoomForm.location" disabled></el-input>
        </el-form-item>
        <el-form-item label="容纳人数">
          <el-input v-model="viewRoomForm.capacity" disabled></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-input v-model="viewRoomForm.status" disabled></el-input>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="viewRoomForm.description" disabled type="textarea" autosize></el-input>
        </el-form-item>
        <el-form-item label="自习室图片">
          <ImagePreview :src="viewRoomForm.image"></ImagePreview>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="viewDialogVisible = false">关闭</el-button>
      </span>
    </el-dialog>

    <!-- 评论对话框 -->
    <el-dialog :visible.sync="commentDialogVisible" title="评论详情" class="comment-dialog">
      <!-- 判断是否有评论，如果没有则显示暂无评论 -->
      <div v-if="comments.length === 0" class="no-comment">
        <p class="center-text">该自习室暂无评论</p>
      </div>
      <!-- 评论内容 -->
      <div v-for="(comment, index) in comments" :key="index" class="comment-item">
        <div class="comment-info">
          <span class="comment-user">评论人：{{ comment.userName }}</span>
          <span class="comment-time">评论时间：{{ comment.createTime }}</span>
        </div>
        <div class="comment-content">
          <p>{{ comment.content }}</p>
          <!-- 删除按钮 -->
          <el-button type="danger" size="mini" @click="handleDeleteComment(comment.commentId)">删除</el-button>
        </div>
      </div>

      <!-- 分割线 -->
      <div class="divider"></div>

      <!-- 新增评论输入框 -->
      <div class="new-comment">
        <el-input v-model="newComment" placeholder="请输入评论内容"></el-input>
        <el-button type="success" size="mini" @click="handleAddComment">发送评论</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

import {addStudyRoom, deleteStudyRoom, getStudyRoom, listStudyRooms, updateStudyRoom} from "../../../api/study/room";
import {addComment, deleteComment, listCommentsByRoomId} from "@/api/study/comment";

export default {
  data() {
    return {
      loading: false,
      studyRoomList: [],
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      dialogVisible: false,
      viewDialogVisible: false, // 查看对话框的显示状态
      commentDialogVisible: false,
      dialogTitle: "",
      dialogButtonText: "",
      comments: [],
      newComment: '',
      roomForm: {
        roomId: null,
        roomName: "",
        location: "",
        capacity: 0,
        status: 1,
        description: "",
        image: "",
      },
      viewRoomForm: {},
    };
  },
  created() {
    this.fetchStudyRooms();
  },
  methods: {
    // 获取自习室列表
    fetchStudyRooms() {
      this.loading = true;
      listStudyRooms(this.queryParams).then((response) => {
        this.studyRoomList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 打开评论对话框并加载评论列表
    openCommentDialog(row) {
      this.selectedRoom = row
      listCommentsByRoomId(row.roomId).then(response => {
        this.comments = response.rows;
        // 打开评论对话框
        this.commentDialogVisible = true;
      }).catch(error => {
        // 处理请求失败的情况
        console.error('获取评论列表失败：', error);
        this.$message.error('获取评论列表失败，请稍后重试');
      });
    },
    // 添加评论
    handleAddComment() {
      // 获取用户输入的评论内容
      const reviewComment = this.newComment.trim();
      if (!reviewComment) {
        // 如果评论内容为空，提示用户输入评论内容
        this.$message.error('请输入评论内容');
        return;
      }
      // 构造评论数据
      const commentData = {
        roomId: this.selectedRoom.roomId, // 课程ID
        content: reviewComment, // 评论内容
      };
      // 调用新增评论的方法
      addComment(commentData).then(response => {
        // 处理新增评论成功的情况
        this.$message.success('评论成功');
        // 清空输入框内容
        this.newComment = '';
        this.commentDialogVisible = false; // 关闭评论对话框
      })
    },
    // 删除评论
    handleDeleteComment(commentId) {
      console.log('删除评论：', commentId)
      deleteComment(commentId).then(response => {
        this.$message.success('删除成功')
        this.commentDialogVisible = false; // 关闭评论对话框
      }).catch(error => {
        console.log('Error:', error)
        this.$message.error('删除失败')
      })
    },
    // 清空表单
    clearForm() {
      this.roomForm = {
        roomId: null,
        roomName: "",
        capacity: 0,
        status: 1,
      };
    },
    handleView(row) {
      this.viewRoomForm = {...row};
      this.viewDialogVisible = true;
    },
    // 新增自习室
    handleAddRoom() {
      this.dialogTitle = "新增自习室";
      this.dialogButtonText = "添加";
      this.clearForm();
      this.dialogVisible = true;
    },
    // 编辑自习室
    handleEdit(room) {
      this.dialogTitle = "编辑自习室";
      this.dialogButtonText = "保存";
      getStudyRoom(room.roomId).then((response) => {
        this.roomForm = response.data;
        this.dialogVisible = true;
      });
    },
    // 提交表单
    handleSubmit() {
      const action = this.roomForm.roomId ? updateStudyRoom : addStudyRoom;
      action(this.roomForm).then(() => {
        this.fetchStudyRooms();
        this.dialogVisible = false;
      });
    },
    // 删除自习室
    handleDelete(room) {
      this.$confirm("确认删除该自习室吗？", "提示", {
        type: "warning",
      }).then(() => {
        deleteStudyRoom(room.roomId).then(() => {
          this.fetchStudyRooms();
        });
      });
    },
  },
};
</script>

<style scoped>
/* 分割线样式 */
.center-text {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  font-size: 15px; /* 设置字体大小为15像素 */
}

/* 评论对话框样式 */
.comment-dialog {
  max-height: 800px; /* 设置最大高度 */
  overflow-y: auto; /* 添加纵向滚动条 */
}

/* 评论样式 */
.comment-item {
  margin-bottom: 20px;
  border: 1px solid #eee;
  padding: 10px;
}

/* 调整评论人和评论时间的间距 */
.comment-info {
  margin-bottom: 5px; /* 增加评论人和评论时间之间的垂直间距 */
  border-bottom: 1px solid #ccc; /* 添加底部边框，实现表格线效果 */
  padding-bottom: 15px; /* 调整底部边框与评论内容之间的间距 */
}

.comment-time {
  margin-left: 300px; /* 增加评论时间与评论人之间的水平间距 */
}

/* 新增评论输入框样式 */
.new-comment {
  margin-top: 20px; /* 设置输入框与上方元素的垂直间距 */
}

/* 调整发送评论按钮的垂直间距 */
.new-comment .el-button {
  margin-top: 10px; /* 设置按钮与上方元素的垂直间距 */
}

/* 分割线样式 */
.divider {
  border-top: 3px solid #ccc;
  margin-top: 20px; /* 调整分割线上方的间距 */
  margin-bottom: 20px; /* 调整分割线下方的间距 */
}
</style>
