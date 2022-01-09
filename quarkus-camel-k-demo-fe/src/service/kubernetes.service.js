import axios from 'axios'

const baseUrl = 'http://quarkus-camel-k-demo.default.local/pods/'

export function getPods(namespace, label, value) {
    let path = ''
    if (namespace) {
        path = namespace
    } else {
        path = 'default'
    }
    if (label) {
        path = '/' + label
    }
    if (value) {
        path = '/' + value
    }

    return axios.get(baseUrl + path);
}

export function getEvents() {
    let path = ''

    return axios.get(baseUrl + path);
}