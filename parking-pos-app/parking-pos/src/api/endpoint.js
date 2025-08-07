import axiosClient from './axiosClient'

const CHECKIN_BASE_URL = import.meta.env.VITE_CHECKIN_API_URL || 'http://localhost:8081'
const CHECKOUT_BASE_URL = import.meta.env.VITE_CHECKOUT_API_URL || 'http://localhost:8083'

export const checkInVehicle = (data) => axiosClient.post(`${CHECKIN_BASE_URL}/checkin`, data)

export const checkOutVehicle = (data) => axiosClient.post(`${CHECKOUT_BASE_URL}/checkout`, data)

export const getActiveTicketPreview = (plateNumber) => axiosClient.get(`${CHECKOUT_BASE_URL}/checkout/preview/${plateNumber}`)  