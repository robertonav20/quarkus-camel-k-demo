<template>
  <ui-grid class="grid">
    <ui-grid-cell :columns="{default:12}" class="cell">
      <ui-card>
        <ui-card-content>
          <ui-card-content style="padding: 15px">
            <ui-card-text>Kubernetes Pods</ui-card-text>
          </ui-card-content>
          <ui-list-divider></ui-list-divider>
          <ui-card-content style="padding: 15px">
            <ui-table
                :data="data"
                :tbody="tbody"
                :thead="thead"
                fullwidth
            >
              <template #actions="{ data }">
                <ui-icon @click="show(data)" style="color: #326ce5">description</ui-icon>
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
            <ui-icon-button icon="refresh" style="color: #326ce5" @click="sendMessage"></ui-icon-button>
          </ui-card-icons>
        </ui-card-actions>
      </ui-card>
    </ui-grid-cell>
  </ui-grid>
</template>

<script>
export default {
  name: 'Kubernetes',
  data() {
    return {
      thead: [
        {
          value: 'id',
          align: 'center'
        },
        {
          value: 'creationDate',
          align: 'center'
        },
        {
          value: 'type',
          align: 'center'
        },
        {
          value: 'actions',
          align: 'center'
        }
      ],
      tbody: [
        {
          value: 'id',
          align: 'center'
        },
        {
          value: 'creationDate',
          align: 'center'
        },
        {
          value: 'type',
          align: 'center'
        },
        {
          value: 'actions',
          align: 'center',
          slot: 'actions'
        }
      ],
      data: [{
        id: '1',
        creationDate: '2021',
        type: 'TYPE'
      }],
      page: 1,
      total: 1,
      webSocketConnection: null
    }
  },
  created: function() {
    console.log("Starting connection to WebSocket Server")
    this.webSocketConnection = new WebSocket('ws://localhost:8081/events/kubernetes')

    this.webSocketConnection.onmessage = function(event) {
      console.log('Received message!')
      console.log(event);
    }

    this.webSocketConnection.onopen = function(event) {
      console.log(event)
      console.log("Successfully connected to the echo websocket server...")
    }
  },
  methods: {
    show(data) {
      console.log(data);
    },
    onPage() {
    },
    sendMessage() {
      console.log('Send message!')
      this.webSocketConnection.send('message')
    }
  }
}
</script>
