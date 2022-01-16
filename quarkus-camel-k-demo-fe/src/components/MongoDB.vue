<template>
  <div class="mongo-container">
    <section>
      <ui-select id="collection-select" v-model="collection" :options="collections">
        Collections
      </ui-select>
    </section>
    <ui-card class="mongo-card">
      <ui-card-content style="height: 100%">
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
                :page-size="pageSize"
                :total="total"
                show-total
            ></ui-pagination>
          </ui-table>
        </ui-card-content>
      </ui-card-content>
    </ui-card>
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
          value: 'Id',
          align: 'center'
        },
        {
          value: 'Date',
          align: 'center'
        },
        {
          value: 'Name',
          align: 'center'
        },
        {
          value: 'Type',
          align: 'center'
        }
      ],
      tbody: [
        {
          fn: data => data.id,
          align: 'center'
        },
        {
          fn: data => data.date,
          align: 'center'
        },
        {
          fn: data => data.name,
          align: 'center'
        },
        {
          fn: data => data.type,
          align: 'center'
        }
      ],
      events: [],
      collectionSelected: 'event.main',
      collections: [
        {
          label: 'Main',
          value: 'event.main'
        },
        {
          label: 'Secondary',
          value: 'event.secondary'
        },
        {
          label: 'Others',
          value: 'event.others'
        }
      ],
      pageIndex: 1,
      pageSize: 5,
      total: 1
    }
  },
  created() {
    this.loadEvents()
  },
  computed: {
    page: {
      get() {
        return this.pageIndex
      },
      set(val) {
        this.pageIndex = val;
        this.loadEvents();
      }
    },
    collection: {
      get() {
        return this.collectionSelected
      },
      set(val) {
        this.collectionSelected = val;
        this.loadEvents();
      }
    }
  },
  methods: {
    loadEvents() {
      getEvents(this.collection, this.pageIndex - 1, this.pageSize)
          .then(response => {
            this.events = response.data
            this.total = Number(response.headers.total)
          })
          .catch(error => console.log(error))
    }
  }
}
</script>

<style scoped>
.mongo-container {
  display: flex;
  flex-direction: column;
  justify-self: center;
  gap: 10px;
  width: 100%;
  padding: 2em;
}

.mongo-card {
  display: flex;
  flex-direction: column;
  justify-content: center;
  height: auto;
  width: 100%;
}
</style>
