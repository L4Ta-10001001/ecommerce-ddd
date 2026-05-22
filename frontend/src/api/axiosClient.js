import axios from 'axios'
import { API_BASE_URL, AUTH_HEADERS } from '../constants'

const axiosClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    ...AUTH_HEADERS,
  },
})

export default axiosClient
