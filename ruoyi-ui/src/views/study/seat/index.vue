<template>
  <div class="app-container">
    <!-- 添加座位按钮 -->
    <el-row :gutter="20" class="mb-20">
      <el-col>
        <el-button type="primary" @click="handleAddSeat" style="margin-bottom: 20px;" v-hasPermi="['study:seat:add']">新增座位</el-button>
      </el-col>
    </el-row>

    <!-- 座位列表 -->
    <el-table :data="seatList" v-loading="loading" style="width: 100%" border>
      <el-table-column label="座位ID" prop="seatId" align="center"></el-table-column>
      <el-table-column label="自习室名称" prop="roomName" align="center"></el-table-column>
      <el-table-column label="座位编号" prop="seatNumber" align="center"></el-table-column>
      <el-table-column label="状态" prop="status" align="center">
        <template #default="scope">
          <el-tag :type="scope.row.status === 0 ? 'danger' : 'success'">
            {{ scope.row.status === 0 ? '不可用' : '可用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="280px">
        <template #default="scope">
          <el-button
            v-if="scope.row.status === 1"
            type="success"
            size="mini"
            @click="handleReserve(scope.row)"
          >
            预约
          </el-button>
          <el-button type="info" size="mini" @click="handleView(scope.row)">查看</el-button>
          <el-button type="primary" size="mini" @click="handleEdit(scope.row)" v-hasPermi="['study:seat:edit']">编辑</el-button>
          <el-button type="danger" size="mini" @click="handleDelete(scope.row)" v-hasPermi="['study:seat:delete']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <pagination
      v-show="totalSeats > 0"
      :total="totalSeats"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="fetchSeats"
    />

    <!-- 预约对话框 -->
    <el-dialog
      :visible.sync="reserveDialogVisible"
      title="预约座位"
      width="30%"
    >
      <el-form :model="reservationForm" label-width="100px">
        <el-form-item label="开始时间">
          <el-date-picker
            v-model="reservationForm.startTime"
            type="datetime"
            placeholder="选择开始时间"
            :picker-options="pickerOptions"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-ddTHH:mm:ssZ"
          />
        </el-form-item>
        <el-form-item label="结束时间">
          <el-date-picker
            v-model="reservationForm.endTime"
            type="datetime"
            placeholder="选择结束时间"
            :picker-options="pickerOptions"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-ddTHH:mm:ssZ"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="reserveDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitReservation">确定</el-button>
      </div>
    </el-dialog>

    <!-- 查看对话框 -->
    <el-dialog :visible.sync="viewDialogVisible" title="查看座位" width="30%">
      <el-form :model="viewSeatForm" label-width="100px">
        <el-form-item label="自习室">
          <el-input v-model="viewSeatForm.roomName" disabled></el-input>
        </el-form-item>
        <el-form-item label="座位编号">
          <el-input v-model="viewSeatForm.seatNumber" disabled></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="viewSeatForm.status" disabled>
            <el-radio :label="0">不可用</el-radio>
            <el-radio :label="1">可用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="viewDialogVisible = false">关闭</el-button>
      </div>
    </el-dialog>

    <!-- 新增/编辑对话框 -->
    <el-dialog :visible.sync="editDialogVisible" :title="dialogTitle" width="30%" @close="resetForm">
      <el-form :model="seatForm" label-width="100px">
        <el-form-item label="自习室">
          <el-select v-model="seatForm.roomId" placeholder="请选择自习室">
            <el-option
              v-for="room in roomOptions"
              :key="room.roomId"
              :label="room.roomName"
              :value="room.roomId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="座位编号">
          <el-input v-model="seatForm.seatNumber"></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="seatForm.status">
            <el-radio :label="0">不可用</el-radio>
            <el-radio :label="1">可用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetForm">取消</el-button>
        <el-button type="primary" @click="submitForm">{{ dialogButtonText }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listSeats, getSeat, addSeat, updateSeat, deleteSeat} from "@/api/study/seat";
import {listStudyRooms} from "@/api/study/room";
import {addReservation} from "@/api/study/reservation";

export default {
  data() {
    return {
      pickerOptions: {
        disabledDate(time) {
          // 获取当前日期
          const today = new Date();
          // 获取一周后的日期
          const oneWeekLater = new Date(today.getTime() + 8 * 24 * 60 * 60 * 1000);
          // 将时间戳转换为年月日格式的字符串
          const currentDate = today.getFullYear() + '-' + (today.getMonth() + 1) + '-' + today.getDate();
          const oneWeekLaterDate = oneWeekLater.getFullYear() + '-' + (oneWeekLater.getMonth() + 1) + '-' + oneWeekLater.getDate();
          // 将当前日期转换为毫秒数
          const currentTime = new Date(currentDate).getTime();
          // 将一周后日期转换为毫秒数
          const oneWeekLaterTime = new Date(oneWeekLaterDate).getTime();
          // 将传入的时间参数转换为毫秒数
          const targetTime = time.getTime();
          // 如果传入时间小于等于当前时间或者大于一周后的时间，则禁用
          return targetTime <= currentTime || targetTime >= oneWeekLaterTime;
        }
      },
      loading: true,
      seatList: [],
      totalSeats: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      reserveDialogVisible: false, // 控制对话框的显示
      viewDialogVisible: false, // 查看对话框的可见性
      editDialogVisible: false, // 新增/编辑对话框的可见性
      dialogTitle: "",
      dialogButtonText: "",
      reservationForm: {
        seatId: null,
        startTime: null,
        endTime: null,
      },
      seatForm: {
        seatId: null,
        roomId: null,
        seatNumber: "",
        status: 1,
      },
      viewSeatForm: {},
      roomOptions: [], // 自习室列表
    };
  },
  created() {
    this.fetchSeats();
    this.fetchRoomOptions();
  },
  methods: {
    fetchSeats() {
      this.loading = true;
      listSeats(this.queryParams).then((response) => {
        this.seatList = response.rows;
        this.totalSeats = response.total;
        this.loading = false;
      });
    },
    fetchRoomOptions() {
      listStudyRooms().then((response) => {
        this.roomOptions = response.rows.map(room => ({
          roomId: room.roomId,
          roomName: room.roomName,
        }));
      });
    },
    handleView(row) {
      this.viewSeatForm = {...row}
      this.viewDialogVisible = true;
    },
    handleAddSeat() {
      this.dialogTitle = "新增座位";
      this.dialogButtonText = "保存";
      this.editDialogVisible = true;
      this.seatForm = {
        seatId: null,
        roomId: null,
        seatNumber: "",
        status: 1,
      };
    },
    handleEdit(row) {
      this.dialogTitle = "编辑座位";
      this.dialogButtonText = "保存";
      this.editDialogVisible = true;
      this.seatForm = {...row};
    },
    handleDelete(row) {
      this.$confirm("确认删除该座位吗？", "提示", {
        type: "warning",
      }).then(() => {
        deleteSeat(row.seatId).then(() => {
          this.$message.success("删除成功");
          this.fetchSeats();
        });
      });
    },
    handleReserve(row) {
      console.log('预约:', row);
      // 填充座位信息到表单
      this.reservationForm.seatId = row.seatId;
      this.reserveDialogVisible = true;
    },
    submitForm() {
      const api = this.seatForm.seatId ? updateSeat : addSeat;
      api(this.seatForm).then(() => {
        this.$message.success(`${this.dialogTitle}成功`);
        this.editDialogVisible = false;
        this.fetchSeats();
      });
    },
    submitReservation() {
      if (!this.reservationForm.startTime || !this.reservationForm.endTime) {
        this.$message.error('请填写完整的预约时间');
        return;
      }
      // 组装预约数据
      const reservationData = {
        ...this.reservationForm,
        status: 0, // 预约状态: 未使用
      };
      addReservation(reservationData)
        .then(() => {
          this.$message.success(`座位 ${this.reservationForm.seatNumber} 已预约成功`);
          this.reserveDialogVisible = false;
          this.reservationForm = {
            seatId: null,
            startTime: null,
            endTime: null,
          };
          this.fetchSeats(); // 刷新座位列表
        })
    },
    resetForm() {
      this.editDialogVisible = false;
      this.seatForm = {
        seatId: null,
        roomId: null,
        seatNumber: "",
        status: 1,
      };
    },
  },
};
</script>
