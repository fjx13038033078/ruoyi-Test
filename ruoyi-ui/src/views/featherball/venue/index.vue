<template>
  <div>
    <!-- 场馆管理页面内容 -->
    <div v-if="activeTab === 'venue'">
      <div class="app-container">
        <!-- 添加场馆按钮 -->
        <el-row :gutter="20" class="mb-20" style="margin-bottom: 20px;">
          <el-col>
            <el-button type="primary" @click="handleAddVenue">新增场馆</el-button>
          </el-col>
        </el-row>

        <!-- 场馆列表 -->
        <el-table :data="venueList" v-loading="loading" style="width: 100%" border>
          <el-table-column label="场馆ID" prop="venueId" align="center"></el-table-column>
          <el-table-column label="场馆名称" prop="venueName" align="center"></el-table-column>
          <el-table-column label="场馆地址" prop="venueAddress" align="center"></el-table-column>
          <el-table-column label="场馆联系方式" prop="venueContact" align="center"></el-table-column>
          <el-table-column label="淋浴设施" prop="isShower" align="center">
            <template slot-scope="scope">
              {{ scope.row.isShower === 1 ? '有' : '无' }}
            </template>
          </el-table-column>
          <el-table-column label="是否户外场馆" prop="isOutdoor" align="center">
            <template slot-scope="scope">
              {{ scope.row.isOutdoor === 1 ? '是' : '否' }}
            </template>
          </el-table-column>
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
          v-show="totalVenues > 0"
          :total="totalVenues"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="fetchVenues"
        />

        <!-- 添加/编辑场馆对话框 -->
        <el-dialog :visible.sync="dialogVisible" :title="dialogTitle" width="30%" @close="handleCloseDialog">
          <!-- 对话框内容 -->
          <div>
            <el-form :model="venueForm" label-width="100px">
<!--              &lt;!&ndash; 场馆ID &ndash;&gt;-->
<!--              <el-form-item label="场馆ID">-->
<!--                <el-input v-model="venueForm.venueId" disabled></el-input>-->
<!--              </el-form-item>-->
              <!-- 场馆名称 -->
              <el-form-item label="场馆名称">
                <el-input v-model="venueForm.venueName" :disabled="isReadOnly"></el-input>
              </el-form-item>
              <!-- 场馆地址 -->
              <el-form-item label="场馆地址">
                <el-input v-model="venueForm.venueAddress" :disabled="isReadOnly"></el-input>
              </el-form-item>
              <!-- 场馆联系方式 -->
              <el-form-item label="场馆联系方式">
                <el-input v-model="venueForm.venueContact" :disabled="isReadOnly"></el-input>
              </el-form-item>
              <!-- 是否有淋浴设施 -->
              <el-form-item label="有无淋浴设施">
                <el-select v-model="venueForm.isShower" :disabled="isReadOnly">
                  <el-option label="有" :value="1"></el-option>
                  <el-option label="无" :value="0"></el-option>
                </el-select>
              </el-form-item>
              <!-- 是否户外场馆 -->
              <el-form-item label="是否户外场馆">
                <el-select v-model="venueForm.isOutdoor" :disabled="isReadOnly">
                  <el-option label="是" :value="1"></el-option>
                  <el-option label="否" :value="0"></el-option>
                </el-select>
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
import { listVenues, addVenue, updateVenue, deleteVenue, getVenue } from '@/api/featherball/venue'

export default {
  data() {
    return {
      // 遮罩层
      loading: true,
      activeTab: 'venue', // 当前激活的选项卡，默认为场馆管理
      venueList: [], // 场馆列表数据
      // 总条数
      totalVenues: 0,
      dialogVisible: false, // 控制新增场馆对话框的显示与隐藏
      dialogTitle: '', // 对话框标题
      dialogButtonText: '', // 对话框按钮文本
      venueForm: { // 新增场馆表单
        venueName: '',
        venueAddress: '',
        venueContact: '',
        isShower: 0, // 默认无淋浴设施
        isOutdoor: 0 // 默认不是户外场馆
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
    // 在页面加载时获取场馆列表
    this.fetchVenues()
  },
  methods: {
    // 获取场馆列表
    fetchVenues() {
      this.loading = true
      listVenues(this.queryParams).then(response => {
        this.venueList = response.rows
        this.totalVenues = response.total
        this.loading = false
      })
    },
    // 清空表单数据
    clearForm() {
      this.venueForm = {
        venueName: '',
        venueAddress: '',
        venueContact: '',
        isShower: 0,
        isOutdoor: 0
      }
    },
    // 添加场馆
    handleAddVenue() {
      this.dialogTitle = "新增场馆"
      this.dialogButtonText = "添加"
      this.isReadOnly = false // 设置为可编辑模式
      this.dialogVisible = true // 打开对话框
    },
    // 添加场馆
    addVenue() {
      // 验证场馆名称是否为空
      if (!this.venueForm.venueName) {
        this.$message.error('场馆名称不能为空')
        return
      }
      // 将布尔值转换为整数值
      this.venueForm.isShower = this.venueForm.isShower ? 1 : 0;
      this.venueForm.isOutdoor = this.venueForm.isOutdoor ? 1 : 0;
      addVenue(this.venueForm).then(response => {
        // 处理添加场馆成功的情况
        // 添加成功后重新获取场馆列表
        this.fetchVenues()
        this.dialogVisible = false // 关闭对话框
        // 清空表单数据
        this.clearForm()
      })
    },
    // 更新场馆
    updateVenue() {
      updateVenue(this.venueForm).then(response => {
        // 处理更新场馆成功的情况
        // 更新成功后重新获取场馆列表
        this.fetchVenues()
        this.dialogVisible = false // 关闭对话框
        // 清空表单数据
        this.clearForm()
        // 将对话框按钮文本设置为其他值，避免再次触发更新操作
        this.dialogButtonText = '更新成功'
      })
    },
    // 删除场馆
    deleteVenue(venueId) {
      deleteVenue(venueId).then(response => {
        // 处理删除场馆成功的情况
        // 删除成功后重新获取场馆列表
        this.fetchVenues()
      })
    },
    // 编辑按钮点击事件
    handleEdit(row) {
      // 将编辑的场馆数据填充到表单中
      this.venueForm = Object.assign({}, row)
      this.dialogTitle = '编辑场馆' // 设置对话框标题为编辑场馆
      this.dialogButtonText = '更新' // 设置对话框按钮文本为更新
      this.isReadOnly = false // 设置为可编辑模式
      this.dialogVisible = true // 打开对话框
    },
    // 提交表单
    handleSubmit() {
      if (this.dialogButtonText === '更新') {
        // 调用更新场馆的方法
        this.updateVenue()
      } else if (this.dialogButtonText === "添加") {
        // 调用添加场馆的方法
        this.addVenue()
      } else {
        this.handleCloseDialog()
      }
    },
    // 删除按钮点击事件
    handleDelete(row) {
      // 弹出确认框，确认删除后调用删除场馆方法
      this.$confirm('确认删除该场馆吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        console.log(row)
        this.deleteVenue(row.venueId)
      })
    },
    // 查看场馆
    handleView(row) {
      this.dialogTitle = '查看场馆' // 设置对话框标题为查看场馆
      this.dialogButtonText = '关闭' // 设置对话框按钮文本为关闭
      this.isReadOnly = true // 设置为只读模式
      // 调用后端接口获取场馆详细信息
      getVenue(row.venueId).then(response => {
        console.log(response)
        this.venueForm = response // 填充表单数据
        this.dialogVisible = true // 打开对话框
        console.log(this.dialogVisible)
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
