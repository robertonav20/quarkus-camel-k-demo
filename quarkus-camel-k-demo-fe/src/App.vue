<template>
  <ui-grid class="grid" style="height: 100%; padding: 0px">
    <ui-grid-cell class="cell" :columns="{default: 3}">
      <ui-drawer type="permanent" nav-id="nav">
        <ui-drawer-header>
          <ui-drawer-title>
            <ui-icon round style="font-size: 30px">monitor</ui-icon>
          </ui-drawer-title>
          <ui-drawer-title>
            <span>{{$route.name}}</span>
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
      </ui-drawer>
    </ui-grid-cell>
    <ui-grid-cell class="demo-cell" :columns="{default: 9}">
      <router-view></router-view>
    </ui-grid-cell>
  </ui-grid>
</template>

<script>
export default {
  name: 'App',
  data() {
    return {
      routes: this.$router.getRoutes()
    }
  }
}
</script>

<style scoped>
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
</style>