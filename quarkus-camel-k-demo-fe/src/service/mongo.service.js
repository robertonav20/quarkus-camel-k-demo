import axios from 'axios'

const baseUrl = 'http://quarkus-camel-k-demo.default.knative.raspberry.home/events/'

export function getEvents(collection, pageIndex, pageSize) {
    let path = ''
    let page = 0
    let size = 5
    if (collection) {
        path = collection
    } else {
        path = 'event.main'
    }
    if (pageIndex) {
        page = pageIndex
    }
    if (pageSize) {
        size = pageSize
    }
    return axios.get(baseUrl + path + '?page=' + page + '&size=' + size);
}