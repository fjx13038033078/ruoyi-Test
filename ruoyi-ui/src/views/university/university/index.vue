<template>
  <div class="app-container">
    <!-- 添加高校按钮 -->
    <el-row :gutter="20" class="mb-20">
      <el-col>
        <el-button type="primary" @click="handleAddUniversity" style="margin-bottom: 20px;" v-hasPermi="['university:university:add']">新增高校</el-button>
      </el-col>
    </el-row>

    <!-- 高校列表 -->
    <el-table :data="universityList" v-loading="loading" border style="width: 100%">
      <el-table-column label="高校ID" prop="universityId" align="center"></el-table-column>
      <el-table-column label="高校名称" prop="universityName" align="center"></el-table-column>
      <el-table-column label="所在省份" prop="location" align="center"></el-table-column>
      <el-table-column label="高校排名" prop="ranking" align="center"></el-table-column>
      <el-table-column label="高校类型" prop="type" align="center"></el-table-column>
      <el-table-column label="是否985" align="center">
        <template #default="scope">
          <span>{{ scope.row.is985 === 1 ? '是' : '否' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="是否211" align="center">
        <template #default="scope">
          <span>{{ scope.row.is211 === 1 ? '是' : '否' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="2024文科最低投档线" prop="minScoreLiberal" align="center"></el-table-column>
      <el-table-column label="2024理科最低投档线" prop="minScoreScience" align="center"></el-table-column>
      <el-table-column label="操作" align="center" width="280px">
        <template #default="scope">
          <el-button type="info" size="mini" @click="handleView(scope.row)">查看</el-button>
          <el-button type="primary" size="mini" @click="handleEdit(scope.row)" v-hasPermi="['university:university:edit']">编辑</el-button>
          <el-button type="danger" size="mini" @click="handleDelete(scope.row)" v-hasPermi="['university:university:delete']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="fetchUniversities"
    />

    <!-- 添加/编辑高校对话框 -->
    <el-dialog :visible.sync="dialogVisible" :title="dialogTitle" width="500px">
      <el-form :model="universityForm" label-width="100px">
        <el-form-item label="高校名称">
          <el-input v-model="universityForm.universityName" placeholder="请输入高校名称" style="width: 200px"></el-input>
        </el-form-item>
        <el-form-item label="所在省份">
          <el-select v-model="universityForm.provinceName" placeholder="请选择省份" style="width:200px">
            <el-option v-for="item in provinceOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="高校排名">
          <el-input-number v-model="universityForm.ranking" :min="0"></el-input-number>
        </el-form-item>
        <el-form-item label="高校类型">
          <el-select v-model="universityForm.type" placeholder="请选择省份" style="width:200px">
            <el-option v-for="item in schoolTypeOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否985">
          <el-switch v-model="universityForm.is985" :active-value="1" :inactive-value="0" active-text="是" inactive-text="否"></el-switch>
        </el-form-item>
        <el-form-item label="是否211">
          <el-switch v-model="universityForm.is211" :active-value="1" :inactive-value="0" active-text="是" inactive-text="否"></el-switch>
        </el-form-item>
        <el-form-item label="2024文科最低投档线">
          <el-input-number v-model="universityForm.minScoreLiberal" :min="0"></el-input-number>
        </el-form-item>
        <el-form-item label="2024理科最低投档线">
          <el-input-number v-model="universityForm.minScoreScience" :min="0"></el-input-number>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">{{ dialogButtonText }}</el-button>
      </div>
    </el-dialog>

    <!-- 查看高校详情对话框 -->
    <el-dialog :visible.sync="viewDialogVisible" title="高校详情" width="40%">
      <el-form :model="viewUniversityForm" label-width="100px">
        <el-form-item label="高校名称">
          <el-input v-model="viewUniversityForm.universityName" disabled></el-input>
        </el-form-item>
        <el-form-item label="所在省份">
          <el-input v-model="viewUniversityForm.location" disabled></el-input>
        </el-form-item>
        <el-form-item label="高校排名">
          <el-input-number v-model="viewUniversityForm.ranking" disabled></el-input-number>
        </el-form-item>
        <el-form-item label="高校类型">
          <el-input v-model="viewUniversityForm.type" disabled></el-input>
        </el-form-item>
        <el-form-item label="是否985">
          <el-input v-model="viewUniversityForm.is985 === 1 ? '是' : '否'" disabled></el-input>
        </el-form-item>
        <el-form-item label="是否211">
          <el-input v-model="viewUniversityForm.is211 === 1 ? '是' : '否'" disabled></el-input>
        </el-form-item>
        <el-form-item label="2024文科最低投档线">
          <el-input-number v-model="viewUniversityForm.minScoreLiberal" disabled></el-input-number>
        </el-form-item>
        <el-form-item label="2024理科最低投档线">
          <el-input-number v-model="viewUniversityForm.minScoreScience" disabled></el-input-number>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="viewDialogVisible = false">关闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { addUniversity, deleteUniversity, getUniversity, listUniversities, updateUniversity } from "@/api/university/university";
import schoolTypeOptions from "@/api/university/schoolTypeOptions";
import provinceOptions from "@/api/university/provinceOptions";

export default {
  data() {
    return {
      schoolTypeOptions,
      provinceOptions,
      loading: false,
      universityList: [],
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      dialogVisible: false,
      viewDialogVisible: false,
      dialogTitle: "",
      dialogButtonText: "",
      universityForm: {
        universityId: null,
        universityName: "",
        location: "",
        ranking: 0,
        type: "",
        is985: 0,
        is211: 0,
        minScoreLiberal: 0,
        minScoreScience: 0,
      },
      viewUniversityForm: {},
    };
  },
  created() {
    this.fetchUniversities();
  },
  methods: {
    provinceOptions() {
      return provinceOptions
    },
    schoolTypeOptions() {
      return schoolTypeOptions
    },
    // 获取高校列表
    fetchUniversities() {
      this.loading = true;
      listUniversities(this.queryParams).then((response) => {
        this.universityList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 查看高校详情
    handleView(row) {
      this.viewUniversityForm = { ...row };
      this.viewDialogVisible = true;
    },
    // 新增高校
    handleAddUniversity() {
      this.dialogTitle = "新增高校";
      this.dialogButtonText = "添加";
      this.clearForm();
      this.dialogVisible = true;
    },
    // 编辑高校
    handleEdit(university) {
      this.dialogTitle = "编辑高校";
      this.dialogButtonText = "保存";
      getUniversity(university.universityId).then((response) => {
        this.universityForm = response.data;
        this.dialogVisible = true;
      });
    },
    // 提交表单
    handleSubmit() {
      const action = this.universityForm.universityId ? updateUniversity : addUniversity;
      action(this.universityForm).then(() => {
        this.$message.success(this.universityForm.universityId ? "修改成功" : "添加成功");
        this.fetchUniversities();
        this.dialogVisible = false;
      });
    },
    // 删除高校
    handleDelete(university) {
      this.$confirm("确认删除该高校信息吗？", "提示", {
        type: "warning",
      }).then(() => {
        deleteUniversity(university.universityId).then(() => {
          this.$message.success("删除成功");
          this.fetchUniversities();
        });
      });
    },
    // 清空表单
    clearForm() {
      this.universityForm = {
        universityId: null,
        universityName: "",
        location: "",
        ranking: 0,
        type: "",
        is985: 0,
        is211: 0,
        minScoreLiberal: 0,
        minScoreScience: 0,
      };
    },
  },
};
</script>

<style scoped>
</style>
