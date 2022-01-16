<template>
  <div class="kubernetes-container">
    <section>
      <ui-select id="collection-select" v-model="namespace" :options="namespaces">
        Namespaces
      </ui-select>
    </section>
    <ui-card class="kubernetes-card">
      <ui-card-content style="height: 100%">
        <ui-card-content style="padding: 15px; background-color: #326ce5">
          <ui-card-text style="color: white">Kubernetes Pods</ui-card-text>
        </ui-card-content>
        <ui-list-divider></ui-list-divider>
        <ui-card-content style="padding: 15px; overflow: auto;">
          <ui-table
              :data="pods"
              :tbody="tbody"
              :thead="thead"
              fullwidth
          >
            <template #actions="{ data }">
              <ui-icon style="color: #326ce5" @click="show(data)">description</ui-icon>
            </template>
          </ui-table>
        </ui-card-content>
      </ui-card-content>
    </ui-card>
  </div>
  <ui-dialog v-model="open">
    <ui-dialog-title>{{ pod.metadata?.name }}</ui-dialog-title>
    <ui-dialog-content>
      {{ pod }}
    </ui-dialog-content>
    <ui-dialog-actions></ui-dialog-actions>
  </ui-dialog>
</template>

<script>
import {getNamespaces, getPods} from "@/service/kubernetes.service";

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
      namespaceSelected: 'default',
      namespaces: [],
      open: false,
      total: 0
    }
  },
  computed: {
    namespace: {
      get() {
        return this.namespaceSelected
      },
      set(val) {
        this.namespaceSelected = val;
        this.loadPods();
      }
    }
  },
  created() {
    this.loadPods();
    this.loadNamespaces();
  },
  methods: {
    show(data) {
      console.log(data);
      this.pod = data;
      this.open = true;
    },
    loadPods() {
      getPods(this.namespaceSelected)
          .then(response => {
            this.pods = response.data
            this.total = response.data.length
          })
          .catch(error => console.log(error))
    },
    loadNamespaces() {
      getNamespaces()
          .then(response => {
            this.namespaces = response.data.map(n => {
              return {
                label: n.metadata.name,
                value: n.metadata.name
              }
            })
          })
          .catch(error => console.log(error))
    }
  }
}
</script>

<style scoped>
.kubernetes-container {
  display: flex;
  flex-direction: column;
  justify-self: center;
  gap: 10px;
  width: 100%;
  padding: 2em;
}

.kubernetes-card {
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 100%;
  height: auto;
  max-height: 700px;
}
</style>