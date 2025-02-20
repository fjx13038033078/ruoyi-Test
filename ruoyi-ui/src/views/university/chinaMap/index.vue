<template>
  <el-card style="height: 1000px;">
    <h3 slot="header">中国高校分布地图</h3>
    <div id="china-map" style="width: 100%; height: 800px;"></div>
  </el-card>
</template>

<script>
import {MapChart} from "echarts/charts";
import {TitleComponent, TooltipComponent, VisualMapComponent} from "echarts/components";
import {CanvasRenderer} from "echarts/renderers";
import chinaMap from "@/assets/geo/china.json";
import shanxiMap from "@/assets/geo/shanxi.json"
import * as echarts from "echarts";
import {listProvinces} from "@/api/university/province";

// 注册必要组件
echarts.use([MapChart, TooltipComponent, VisualMapComponent, TitleComponent, CanvasRenderer]);

export default {
  data() {
    return {
      mapData: [], // 存放高校数据
      chartInstance: null, // 存储 ECharts 实例
    };
  },
  mounted() {
    this.fetchProvincesData(); // 页面加载时调用接口
  },
  methods:{
    /** 获取省份高校数据 */
    async fetchProvincesData() {
      try {
        const response = await listProvinces({});
        if (response.code === 200) {
          this.mapData = response.rows.map((item) => ({
            name: item.provinceName, // 省份名称
            value: item.numTotal, // 高校总数
            num985: item.num985, // 985高校数
            num211: item.num211, // 211高校数
            minScoreLiberal: item.minScoreLiberal,
            minScoreScience: item.minScoreScience,
          }));
          this.initChinaMap(); // 数据加载后初始化地图
        } else {
          console.error("数据加载失败:", response.msg);
        }
      } catch (error) {
        console.error("请求失败:", error);
      }
    },
    /** 初始化中国地图 */
    initChinaMap() {
      if (!this.chartInstance) {
        this.chartInstance = echarts.init(this.$el.querySelector("#china-map"));
      }

      // 注册中国地图
      echarts.registerMap("china", chinaMap);

      // 配置项
      const option = {
        title: {
          text: "高校地理分布图",
          subtext: "数据来源：中国教育考试网等",
          left: "center",
        },
        tooltip: {
          trigger: "item",
          formatter: (params) => {
            const { name, data } = params;
            const value = data?.value || "暂无数据";
            const num985 = data?.num985 || "暂无数据";
            const num211 = data?.num211 || "暂无数据";
            const minScoreLiberal = data?.minScoreLiberal || "暂无数据";
            const minScoreScience = data?.minScoreScience || "暂无数据";
            return `${name}<br/>省内高校总数：${value}<br/>省内985高校：${num985}<br/>省内211高校：${num211}<br/>2024文科最低投档线：${minScoreLiberal}<br/>2024理科最低投档线：${minScoreScience}`;
          },
        },
        visualMap: {
          min: 10,
          max: 100,
          left: "left",
          top: "bottom",
          text: ["高", "低"], // 文本
          inRange: {
            color: ["#e0f3f8", "#abd9e9", "#74add1", "#4575b4", "#313695"],
          },
          calculable: true,
        },
        series: [
          {
            name: "人口分布",
            type: "map",
            map: "china", // 使用注册的中国地图
            emphasis: {
              label: {
                show: true,
              },
            },
            data: this.mapData, // 绑定动态数据
          },
        ],
      };
      // 设置选项
      this.chartInstance.setOption(option);

      // 监听点击事件，切换到省份地图
      this.chartInstance.on("click", (params) => {
        if (params.name === "山西") {
          this.showProvinceMap("山西", shanxiMap);
        }
      });
    },
    showProvinceMap(provinceName, provinceGeoJSON) {
      echarts.registerMap(provinceName, provinceGeoJSON);

      const option = {
        title: {
          text: `${provinceName}高校分布图`,
          left: "center",
        },
        tooltip: {
          trigger: "item",
          formatter: (params) => {
            const { name, data } = params;
            return `${name}：${data?.value || "暂无数据"}`;
          },
        },
        visualMap: {
          min: 1,
          max: 10,
          left: "left",
          top: "bottom",
          text: ["高", "低"],
          inRange: {
            color: ["#e0f3f8", "#abd9e9", "#74add1", "#4575b4", "#313695"],
          },
          calculable: true,
        },
        series: [
          {
            name: `${provinceName}高校分布`,
            type: "map",
            map: provinceName,
            emphasis: {
              label: { show: true },
            },
            data: this.mapData.filter(item => item.name === provinceName),
          },
        ],
      };

      this.chartInstance.setOption(option);
    },
  }
}
</script>


<style scoped lang="scss">

</style>
