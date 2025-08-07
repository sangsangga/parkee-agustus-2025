import { useState } from 'react'
import { parkingService } from '../api/parkingService'

function CheckOut() {
  const [plateNumber, setPlateNumber] = useState('')
  const [message, setMessage] = useState('')
  const [checkoutResult, setCheckoutResult] = useState(null)
  const [isLoading, setIsLoading] = useState(false)

  const handleSubmit = async (e) => {
    e.preventDefault()
    
    if (!plateNumber.trim()) {
      setMessage('Please enter a valid plate number')
      return
    }

    setIsLoading(true)
    setMessage('')

    try {
      const result = await parkingService.checkOut(plateNumber.toUpperCase())
      
      if (result.success) {
        setCheckoutResult(result.data)
        setPlateNumber('')
        setMessage('Checkout completed successfully!')
      } else {
        setMessage(result.error || 'Failed to process checkout')
      }
    } catch (error) {
      setMessage('An unexpected error occurred')
      console.error('Check-out error:', error)
    } finally {
      setIsLoading(false)
    }
  }

  const handleNewCheckout = () => {
    setCheckoutResult(null)
    setMessage('')
  }

  const formatDuration = (checkInTime, checkOutTime) => {
    const duration = new Date(checkOutTime) - new Date(checkInTime)
    const hours = Math.floor(duration / (1000 * 60 * 60))
    const minutes = Math.floor((duration % (1000 * 60 * 60)) / (1000 * 60))
    return `${hours}h ${minutes}m`
  }

  return (
    <div className="checkout-container">
      <h2>Vehicle Check-Out</h2>
      
      {!checkoutResult ? (
        <form onSubmit={handleSubmit} className="checkout-form">
          <div className="form-group">
            <label htmlFor="plateNumber">Vehicle Plate Number:</label>
            <input
              type="text"
              id="plateNumber"
              value={plateNumber}
              onChange={(e) => setPlateNumber(e.target.value)}
              placeholder="Enter plate number (e.g., ABC123)"
              className="form-input"
              autoFocus
              disabled={isLoading}
            />
          </div>
          
          <button 
            type="submit" 
            className="submit-btn"
            disabled={isLoading}
          >
            {isLoading ? 'Processing Check-Out...' : 'Process Check-Out'}
          </button>
          
          {message && (
            <div className={`message ${message.includes('successfully') ? 'success' : 'error'}`}>
              {message}
            </div>
          )}
        </form>
      ) : (
        <div className="checkout-display">
          <h3>Check-Out Completed!</h3>
          <div className="ticket-info">
            <p><strong>Plate Number:</strong> {checkoutResult.plateNumber}</p>
            <p><strong>Check-in Time:</strong> {new Date(checkoutResult.checkInTime).toLocaleString()}</p>
            <p><strong>Check-out Time:</strong> {new Date(checkoutResult.checkOutTime).toLocaleString()}</p>
            <p><strong>Duration:</strong> {formatDuration(checkoutResult.checkInTime, checkoutResult.checkOutTime)}</p>
            <p><strong>Total Price:</strong> ${checkoutResult.totalPrice?.toFixed(2) || '0.00'}</p>
            <p><strong>Rate:</strong> $5.00 per hour</p>
          </div>
          <button onClick={handleNewCheckout} className="new-checkout-btn">
            Process Another Check-Out
          </button>
        </div>
      )}
    </div>
  )
}

export default CheckOut
