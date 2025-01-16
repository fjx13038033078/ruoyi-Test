<template>
  <div class="app-container">
    <!-- 添加自习室按钮 -->
    <el-row :gutter="20" class="mb-20">
      <el-col>
        <el-button type="primary" @click="handleAddRoom" style="margin-bottom: 20px;">新增自习室</el-button>
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
          <el-button type="info" size="mini" @click="handleView(scope.row)">查看</el-button>
          <el-button type="primary" size="mini" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button>
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
  </div>
</template>

<script>

import {addStudyRoom, deleteStudyRoom, getStudyRoom, listStudyRooms, updateStudyRoom} from "../../../api/study/room";

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
      dialogTitle: "",
      dialogButtonText: "",
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
