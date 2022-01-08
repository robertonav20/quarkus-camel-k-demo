<template>
  <ui-grid class="grid">
    <ui-grid-cell :columns="{default:12}" class="cell">
      <ui-card>
        <ui-card-content>
          <ui-card-content style="padding: 15px">
            <ui-card-text><ui-chip style="background-color: #326ce5; color: white">Mongo Events</ui-chip></ui-card-text>
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
                <ui-icon @click="show(data)" style="color: #be1131">delete</ui-icon>
              </template>
              <ui-pagination
                  v-model="page"
                  :total="total"
                  show-total
                  @change="onPage">
              </ui-pagination>
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
    </ui-grid-cell>
  </ui-grid>
</template>

<script>
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
      total: 1
    }
  },
  methods: {
    show(data) {
      console.log(data);
    },
    onPage() {
    }
  }
}
</script>
