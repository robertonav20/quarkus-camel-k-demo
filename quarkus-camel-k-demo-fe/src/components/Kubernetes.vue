<template>
  <div class="kubernetes-container">
    <div class="kubernetes-card">
      <ui-card style="width: 100%;">
        <ui-card-content>
          <ui-card-content style="padding: 15px; background-color: #326ce5">
            <ui-card-text style="color: white">Kubernetes Pods</ui-card-text>
          </ui-card-content>
          <ui-list-divider></ui-list-divider>
          <ui-card-content style="padding: 15px">
            <ui-table
                :data="pods"
                :tbody="tbody"
                :thead="thead"
                fullwidth
            >
              <template #actions="{ data }">
                <ui-icon style="color: #326ce5" @click="show(data)">description</ui-icon>
              </template>
              <ui-pagination
                  v-model="page"
                  :total="total"
                  show-total
                  @change="onPage"
              ></ui-pagination>
            </ui-table>
          </ui-card-content>
        </ui-card-content>
        <ui-divider></ui-divider>
        <ui-card-actions>
          <ui-card-icons>
            <ui-icon-button icon="refresh" style="color: #326ce5"></ui-icon-button>
          </ui-card-icons>
        </ui-card-actions>
      </ui-card>
    </div>
  </div>
  <ui-dialog v-model="open">
    <ui-dialog-title>{{pod.metadata?.name}}</ui-dialog-title>
    <ui-dialog-content>
      {{pod}}
    </ui-dialog-content>
    <ui-dialog-actions></ui-dialog-actions>
  </ui-dialog>
</template>

<script>
import {getPods} from "@/service/kubernetes.service";

export default {
  name: 'Kubernetes',
  data() {
    return {
      thead: [
        {
          value: 'Ip',
          align: 'center'
        },
        {
          value: 'Name',
          align: 'center'
        },
        {
          value: 'Status',
          align: 'center'
        },
        {
          value: 'Namespace',
          align: 'center'
        },
        {
          value: 'actions',
          align: 'center'
        }
      ],
      tbody: [
        {
          fn: data => data.status.podIP,
          align: 'center'
        },
        {
          fn: data => data.metadata.name,
          align: 'center'
        },
        {
          fn: data => data.status.phase,
          align: 'center'
        },
        {
          fn: data => data.metadata.namespace,
          align: 'center'
        },
        {
          value: 'actions',
          align: 'center',
          slot: 'actions'
        }
      ],
      pod: {},
      pods: [],
      open: false,
      page: 1,
      total: 1,
      webSocketConnection: null
    }
  },
  created() {
    getPods()
        .then(response => {
          this.pods = response.data
          this.total = response.data.length
        })
        .catch(error => console.log(error))
  },
  methods: {
    show(data) {
      console.log(data);
      this.pod = data;
      this.open = true;
    },
    onPage() {
    }
  }
}
</script>

<style scoped>
.kubernetes-container {
  display: flex;
  justify-content: center;
  justify-self: center;
  width: 100%;
  padding: 2em;
}
.kubernetes-card {
  display: flex;
  flex-direction: column;
  justify-content: center;
  height: 100%;
  width: 100%;
}
</style>