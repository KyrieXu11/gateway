<template>
  <div class="dashboard-editor-container">
    <!--<github-corner class="github-corner" />-->
    <panel-group :data="panelGroupData" />
    <el-row :gutter="32">
      <el-col :xs="24" :sm="24" :lg="16">
        <div class="chart-wrapper">
          <line-chart :chart-data="lineChartData" />
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <pie-chart :chart-data="pieChartData" />
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import PanelGroup from '@/views/dashboard/admin/components/PanelGroup'
import LineChart from '@/views/dashboard/admin/components/LineChart'
import PieChart from '@/views/dashboard/admin/components/PieChart'
import { flowStat, panelGroupData, serviceStat } from '@/api/dashboard'

export default {
  name: 'AdminDashboard',
  components: {
    PanelGroup,
    LineChart,
    PieChart
    // GithubCorner
  },
  data() {
    return {
      panelGroupData: {
        service_num: 23,
        today_request_num: 1200,
        current_qps: 200,
        app_num: 5
      },
      lineChartData: {
        title: '今日流量统计',
        today: [220, 182, 191, 134, 150, 120, 110, 125, 145, 122, 165, 122],
        yesterday: [120, 110, 125, 145, 122, 165, 122, 220, 182, 191, 134, 150]
      },
      pieChartData: {
        title: '服务占比',
        legend: [],
        series: []
      }
    }
  },
  created() {
    this.fetchPanelGroupData()
    this.fetchFlowStat()
    this.fetchServiceStat()
  },
  methods: {
    fetchPanelGroupData(id) {
      panelGroupData().then(response => {
        this.panelGroupData = response.data
      })
    },
    fetchFlowStat(id) {
      flowStat().then(response => {
        this.lineChartData.today = response.data.today
        this.lineChartData.yesterday = response.data.yesterday
      })
    },
    fetchServiceStat(id) {
      serviceStat().then(response => {
        this.pieChartData.legend = response.data.legend
        this.pieChartData.series = response.data.data
      })
    },
    handleSetLineChartData(type) {
      // this.lineChartData = lineChartData[type]
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .github-corner {
    position: absolute;
    top: 0px;
    border: 0;
    right: 0;
  }

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width: 1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
