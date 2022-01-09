import axios from 'axios'

const baseUrl = 'http://quarkus-camel-k-demo.default.local/mongo/'

export function getEvents() {
    let path = ''

    return axios.get(baseUrl + path);
}