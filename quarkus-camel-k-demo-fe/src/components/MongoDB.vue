<template>
  <div class="mongo-container">
    <div class="mongo-card">
      <ui-card style="width: 100%;">
        <ui-card-content>
          <ui-card-content style="padding: 15px; background-color: #326ce5">
            <ui-card-text style="color: white">Mongo Events</ui-card-text>
          </ui-card-content>
          <ui-list-divider></ui-list-divider>
          <ui-card-content style="padding: 15px">
            <ui-table
                :data="events"
                :tbody="tbody"
                :thead="thead"
                fullwidth
            >
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
</template>

<script>
import {getEvents} from "@/service/mongo.service";

export default {
  name: 'MongoDB',
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
        }
      ],
      events: [],
      page: 1,
      total: 1
    }
  },
  created() {
    this.loadEvents()
  },
  methods: {
    loadEvents() {
      getEvents()
        .then(response => {
          this.events = response.data
          this.total = response.data.length
        })
        .catch(error => console.log(error))
    },
    onPage() {}
  }
}
</script>

<style scoped>
.mongo-container {
  display: flex;
  justify-content: center;
  justify-self: center;
  width: 100%;
  padding: 2em;
}

.mongo-card {
  display: flex;
  flex-direction: column;
  justify-content: center;
  height: 100%;
  width: 100%;
}
</style>
