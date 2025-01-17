<template>
  <div>
    <!-- 预约管理页面内容 -->
    <div v-if="activeTab === 'reservation'">
      <div class="app-container">
        <!-- 预约列表 -->
        <el-table :data="reservationList" v-loading="loading" style="width: 100%" border>
          <el-table-column label="预约ID" prop="reservationId" align="center"></el-table-column>
          <el-table-column label="座位编号" prop="seatNumber" align="center"></el-table-column>
          <el-table-column label="自习室名称" prop="roomName" align="center"></el-table-column>
          <el-table-column label="用户名称" prop="userName" align="center"></el-table-column>
          <el-table-column label="开始时间" prop="startTime" align="center">
            <template slot-scope="scope">
              {{ formatDate(scope.row.startTime) }}
            </template>
          </el-table-column>
          <el-table-column label="结束时间" prop="endTime" align="center">
            <template slot-scope="scope">
              {{ formatDate(scope.row.endTime) }}
            </template>
          </el-table-column>
          <el-table-column label="预约状态" prop="status" align="center">
            <template slot-scope="scope">
              {{ getStatusText(scope.row.status) }}
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="220px">
            <template slot-scope="scope">
              <!-- 取消预约按钮 -->
              <el-button type="danger" size="mini" @click="cancelReservation(scope.row)" v-hasPermi="['study:reservation:cancel']">取消预约</el-button>
              <!-- 删除预约按钮 -->
              <el-button type="danger" size="mini" @click="deleteReservation(scope.row)" v-hasPermi="['study:reservation:delete']" style="margin-left: 10px;">删除预约</el-button>
            </template>
          </el-table-column>
        </el-table>

        <!-- 分页组件 -->
        <pagination
          v-show="totalReservations > 0"
          :total="totalReservations"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="fetchReservations"
        />
      </div>
    </div>
  </div>
</template>

<script>
import { listReservations, cancelReservation, deleteReservation } from '@/api/study/reservation'

export default {
  data() {
    return {
      loading: true,
      activeTab: 'reservation',
      reservationList: [],
      totalReservations: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10
      }
    };
  },
  created() {
    this.fetchReservations();
  },
  methods: {
    // 格式化时间为 'yyyy-MM-dd HH:mm:ss'
    formatDate(date) {
      const d = new Date(date);
      const year = d.getFullYear();
      const month = String(d.getMonth() + 1).padStart(2, '0'); // 补零
      const day = String(d.getDate()).padStart(2, '0');
      const hours = String(d.getHours()).padStart(2, '0');
      const minutes = String(d.getMinutes()).padStart(2, '0');
      const seconds = String(d.getSeconds()).padStart(2, '0');
      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    },

    // 获取预约状态文本
    getStatusText(status) {
      switch (status) {
        case 0:
          return '未使用';
        case 1:
          return '已使用';
        case 2:
          return '已取消';
        default:
          return '未知状态';
      }
    },

    // 获取预约记录
    fetchReservations() {
      this.loading = true;
      listReservations(this.queryParams).then(response => {
        this.reservationList = response.rows;
        this.totalReservations = response.total;
        this.loading = false;
      });
    },

    // 取消预约
    cancelReservation(row) {
      this.$confirm('确认取消该预约吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        cancelReservation(row.reservationId).then(() => {
          this.$message.success('取消预约成功');
          this.fetchReservations();
        });
      });
    },

    // 删除预约
    deleteReservation(row) {
      this.$confirm('确认删除该预约吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteReservation(row.reservationId).then(() => {
          this.$message.success('删除预约成功');
          this.fetchReservations();
        });
      });
    }
  }
};
</script>

<style scoped>
/* 可以根据需要自定义样式 */
</style>
