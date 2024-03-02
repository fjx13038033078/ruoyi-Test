<template>
  <div>
    <!-- 场地管理页面内容 -->
    <div v-if="activeTab === 'court'">
      <div class="app-container">
        <!-- 添加场地按钮 -->
        <el-row :gutter="20" class="mb-20" style="margin-bottom: 20px;">
          <el-col>
            <el-button type="primary" @click="handleAddCourt">新增场地</el-button>
          </el-col>
        </el-row>

        <!-- 场地列表 -->
        <el-table :data="courtList" v-loading="loading" style="width: 100%" border>
          <el-table-column label="场地ID" prop="courtId" align="center"></el-table-column>
          <el-table-column label="场馆名称" prop="venueName" align="center"></el-table-column>
          <el-table-column label="场地编号" prop="courtNumber" align="center"></el-table-column>
          <el-table-column label="场地状态" prop="courtStatus" align="center">
            <template slot-scope="scope">
              {{ scope.row.courtStatus === 0 ? '可预约' : '不可预约' }}
            </template>
          </el-table-column>
          <el-table-column label="场地费用" prop="courtFee" align="center"></el-table-column>
          <el-table-column label="操作" align="center" width="230px">
            <template slot-scope="scope">
              <el-button type="success" size="mini" @click="handleView(scope.row)">查看</el-button>
              <el-button type="primary" size="mini" @click="handleEdit(scope.row)">编辑</el-button>
              <el-button type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <!-- 分页组件 -->
        <pagination
          v-show="totalCourts > 0"
          :total="totalCourts"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="fetchCourts"
        />

        <!-- 添加/编辑场地对话框 -->
        <el-dialog :visible.sync="dialogVisible" :title="dialogTitle" width="30%" @close="handleCloseDialog">
          <!-- 对话框内容 -->
          <div>
            <el-form :model="courtForm" label-width="100px">
              <!-- 场馆名称下拉选择 -->
              <el-form-item label="场馆名称">
                <el-select v-model="courtForm.venueId" placeholder="请选择场馆" :disabled="isReadOnly">
                  <el-option
                    v-for="venue in venueOptions"
                    :key="venue.venueId"
                    :label="venue.venueName"
                    :value="venue.venueId"
                  ></el-option>
                </el-select>
              </el-form-item>
              <!-- 场地编号 -->
              <el-form-item label="场地编号">
                <el-input v-model="courtForm.courtNumber" :disabled="isReadOnly"></el-input>
              </el-form-item>
              <!-- 场地状态 -->
              <el-form-item label="场地状态">
                <el-radio-group v-model="courtForm.courtStatus" :disabled="isReadOnly">
                  <el-radio :label="0">可预约</el-radio>
                  <el-radio :label="1">不可预约</el-radio>
                </el-radio-group>
              </el-form-item>
              <!-- 场地费用 -->
              <el-form-item label="场地费用">
                <el-input v-model="courtForm.courtFee" :disabled="isReadOnly"></el-input>
              </el-form-item>
            </el-form>
          </div>
          <!-- 对话框按钮 -->
          <div slot="footer" class="dialog-footer" style="text-align: center;">
            <el-button @click="handleCloseDialog">取消</el-button>
            <el-button type="primary" @click="handleSubmit">{{ dialogButtonText }}</el-button>
          </div>
        </el-dialog>

      </div>
    </div>
  </div>
</template>

<script>
import { listCourts, addCourt, updateCourt, deleteCourt, getCourt } from '@/api/featherball/court'
import {listVenues} from "@/api/featherball/venue";

export default {
  data() {
    return {
      // 遮罩层
      loading: true,
      activeTab: 'court', // 当前激活的选项卡，默认为场地管理
      courtList: [], // 场地列表数据
      venueOptions: [], // 场馆选项列表
      // 总条数
      totalCourts: 0,
      dialogVisible: false, // 控制新增场地对话框的显示与隐藏
      dialogTitle: '', // 对话框标题
      dialogButtonText: '', // 对话框按钮文本
      courtForm: { // 新增场地表单
        venueName: '',
        courtNumber: '',
        courtStatus: 0, // 默认可预约
        courtFee: ''
      },
      isReadOnly: false, // 是否只读模式
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10
      }
    }
  },
  created() {
    // 在页面加载时获取场地列表
    this.fetchCourts()
    this.fetchVenues()
  },
  methods: {
    // 获取场地列表
    fetchCourts() {
      this.loading = true
      listCourts(this.queryParams).then(response => {
        this.courtList = response.rows
        this.totalCourts = response.total
        this.loading = false
      })
    },
    // 获取场馆列表
    fetchVenues() {
      listVenues().then(response => {
        // 将后端返回的场馆列表赋值给 venueOptions
        this.venueOptions = response.rows.map(venue => ({
          venueId: venue.venueId,
          venueName: venue.venueName
        }));
      });
    },
    // 清空表单数据
    clearForm() {
      this.courtForm = {
        venueName: '',
        courtNumber: '',
        courtStatus: 0,
        courtFee: ''
      }
    },
    // 添加场地
    handleAddCourt() {
      this.dialogTitle = "新增场地"
      this.dialogButtonText = "添加"
      this.isReadOnly = false // 设置为可编辑模式
      this.dialogVisible = true // 打开对话框
    },
    // 添加场地
    addCourt() {
      // 验证场地名称是否为空
      if (!this.courtForm.venueId) {
        this.$message.error('场馆名称不能为空')
        return
      }
      // 将布尔值转换为整数值
      this.courtForm.courtStatus = this.courtForm.courtStatus ? 1 : 0;
      addCourt(this.courtForm).then(response => {
        // 处理添加场地成功的情况
        // 添加成功后重新获取场地列表
        this.fetchCourts()
        this.dialogVisible = false // 关闭对话框
        // 清空表单数据
        this.clearForm()
      })
    },
    // 更新场地
    updateCourt() {
      updateCourt(this.courtForm).then(response => {
        // 处理更新场地成功的情况
        // 更新成功后重新获取场地列表
        this.fetchCourts()
        this.dialogVisible = false // 关闭对话框
        // 清空表单数据
        this.clearForm()
        // 将对话框按钮文本设置为其他值，避免再次触发更新操作
        this.dialogButtonText = '更新成功'
      })
    },
    // 删除场地
    deleteCourt(courtId) {
      deleteCourt(courtId).then(response => {
        // 处理删除场地成功的情况
        // 删除成功后重新获取场地列表
        this.fetchCourts()
      })
    },
    // 编辑按钮点击事件
    handleEdit(row) {
      // 将编辑的场地数据填充到表单中
      this.courtForm = Object.assign({}, row)
      this.dialogTitle = '编辑场地' // 设置对话框标题为编辑场地
      this.dialogButtonText = '更新' // 设置对话框按钮文本为更新
      this.isReadOnly = false // 设置为可编辑模式
      this.dialogVisible = true // 打开对话框
    },
    // 提交表单
    handleSubmit() {
      if (this.dialogButtonText === '更新') {
        // 调用更新场地的方法
        this.updateCourt()
      } else if (this.dialogButtonText === "添加") {
        // 调用添加场地的方法
        this.addCourt()
      } else {
        this.handleCloseDialog()
      }
    },
    // 删除按钮点击事件
    handleDelete(row) {
      // 弹出确认框，确认删除后调用删除场地方法
      this.$confirm('确认删除该场地吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        console.log(row)
        this.deleteCourt(row.courtId)
      })
    },
    // 查看场地
    handleView(row) {
      this.dialogTitle = '查看场地' // 设置对话框标题为查看场地
      this.dialogButtonText = '关闭' // 设置对话框按钮文本为关闭
      this.isReadOnly = true // 设置为只读模式
      // 调用后端接口获取场地详细信息
      getCourt(row.courtId).then(response => {
        this.courtForm = response // 填充表单数据
        this.dialogVisible = true // 打开对话框
      })
    },
    // 关闭对话框
    handleCloseDialog() {
      this.clearForm()
      this.dialogVisible = false
    }
  }
}
</script>

