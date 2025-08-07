import { checkInVehicle, checkOutVehicle } from './endpoint'

export const parkingService = {
  // Check-in a vehicle
  async checkIn(plateNumber) {
    try {
      const response = await checkInVehicle({ plateNumber })
      return {
        success: true,
        data: response.data.data,
        message: response.data.message
      }
    } catch (error) {
      console.error('Check-in error:', error)
      return {
        success: false,
        error: error.response?.data?.message || 'Failed to check-in vehicle',
        details: error.response?.data
      }
    }
  },

  // Check-out a vehicle
  async checkOut(plateNumber) {
    try {
      const response = await checkOutVehicle({ plateNumber })
      return {
        success: true,
        data: response.data.data,
        message: response.data.message
      }
    } catch (error) {
      console.error('Check-out error:', error)
      return {
        success: false,
        error: error.response?.data?.message || 'Failed to check-out vehicle',
        details: error.response?.data
      }
    }
  }
}
