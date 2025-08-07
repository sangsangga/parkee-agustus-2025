import axios from 'axios'

const axiosClient = axios.create({
  timeout: 10_000,
  headers: {
    'Content-Type': 'application/json',
  },
})

export default axiosClient