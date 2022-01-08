<template>
  <div class="layout" style="height: 100%; padding: 0px">
    <div class="sidebar" :columns="{default: 2}">
      <ui-drawer nav-id="nav" type="permanent">
        <ui-drawer-header>
          <ui-drawer-title>
            <ui-icon round style="font-size: 30px">monitor</ui-icon>
          </ui-drawer-title>
          <ui-drawer-title>
            <span>{{ $route.name }}</span>
          </ui-drawer-title>
          <ui-drawer-subtitle>Dashboard</ui-drawer-subtitle>
        </ui-drawer-header>
        <ui-drawer-content>
          <ui-nav>
            <ui-nav-item v-for="(route, index) in routes" :key="index" :href="route.path">
              <ui-icon style="color: #326ce5">{{ route.meta.icon }}</ui-icon>
              {{ route.name }}
            </ui-nav-item>
          </ui-nav>
        </ui-drawer-content>
        <ui-drawer-content class="drawer-footer">
          <div class="drawer-footer-container">
            <span>Status</span>
            <div :style="statusClass"></div>
          </div>
        </ui-drawer-content>
      </ui-drawer>
    </div>
    <div class="content">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
export default {
  name: 'App',
  data() {
    return {
      routes: this.$router.getRoutes(),
      status: false
    }
  },
  computed: {
    statusClass() {
      return {
        backgroundColor: this.status ? '#00FF00FF' : '#FF0000FF',
        borderRadius: '30px',
        width: '20px',
        height: '20px'
      }
    }
  },
  mounted: function () {
    const component = this
    console.log("Starting connection to WebSocket Server")
    this.webSocketConnection = new WebSocket('ws://localhost:8081/sessions/frontend')

    this.webSocketConnection.onmessage = function (event) {
      console.log('Received message!')
      console.log(event);
      if (event.data) {
        const data = JSON.parse(event.data)
        component.$toast(data.message)
        if (data.type === 'START') {
          component.status = true;
        }
        if (data.type === 'STOP') {
          component.status = false;
        }
      }
    }
    this.webSocketConnection.onopen = function (event) {
      console.log(event)
      console.log("Successfully connected to the echo websocket server...")
    }
  }
}
</script>

<style scoped>
.layout {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
.sidebar {
  width: auto;
}
.content {
  display: flex;
  justify-content: center;
  width: 100%;
  height: 100%;
  padding: 0 3em;
}

.mdc-drawer__header {
  background-color: #326ce5;
  color: white !important;
}

.mdc-drawer__title {
  color: white !important;
}

.mdc-drawer__subtitle {
  color: white !important;
}

.drawer-footer {
  height: auto;
  bottom: 0;
  overflow: unset;
  color: white;
  background: #326ce5;
}

.drawer-footer-container {
  padding: 24px;
  display: flex;
  justify-content: space-between;
}
</style>