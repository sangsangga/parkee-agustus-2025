import { checkInVehicle, checkOutVehicle, getActiveTicketPreview } from './endpoint'

export const parkingService = {
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
  },

  async getActiveTicketPreview(plateNumber) {
    try {
      const response = await getActiveTicketPreview({ plateNumber })
      return {
        success: true,
        data: response.data.data,
        message: response.data.message
      }
    } catch (error) {
      console.error('Get active ticket preview error:', error)
      return {
        success: false,
        error: error.response?.data?.message || 'Failed to get active ticket preview',
        details: error.response?.data
      }
    }
  }
}
