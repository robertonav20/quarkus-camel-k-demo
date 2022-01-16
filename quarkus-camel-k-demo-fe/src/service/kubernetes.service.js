import axios from 'axios'

const baseUrl = 'http://quarkus-camel-k-demo.default.local/'

export function getPods(namespace, label, value) {
    let path = 'pods/'
    if (namespace) {
        path += namespace
    } else {
        path += 'default'
    }
    if (label) {
        path += '/' + label
    }
    if (value) {
        path += '/' + value
    }

    return axios.get(baseUrl + path);
}

export function getNamespaces() {
    return axios.get(baseUrl + 'namespaces');
}