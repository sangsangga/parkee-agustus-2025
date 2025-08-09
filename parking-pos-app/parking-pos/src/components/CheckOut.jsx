import { useState } from 'react'
import { parkingService } from '../api/parkingService'

function CheckOut() {
  const [plateNumber, setPlateNumber] = useState('')
  const [message, setMessage] = useState('')

  const [checkoutResult, setCheckoutResult] = useState(null)
  const [isLoading, setIsLoading] = useState(false)

  const [ticketPreview, setTicketPreview] = useState(null)
  const [isPreviewLoading, setIsPreviewLoading] = useState(false)


  const handlePreviewTicket = async (e) => {
    e.preventDefault()
    
    if (!plateNumber.trim()) {
      setMessage('Please enter a valid plate number')
      return
    }

    setIsPreviewLoading(true)
    setMessage('')

    try {
      const result = await parkingService.getActiveTicketPreview(plateNumber.toUpperCase())
      
      if (result.success) {
        setTicketPreview(result.data)
        setMessage('')
      } else {
        setMessage(result.error || 'Failed to get ticket information')
      }
    } catch (error) {
      setMessage('An unexpected error occurred')
      console.error('Preview error:', error)
    } finally {
      setIsPreviewLoading(false)
    }
  }

  const handleConfirmCheckout = async () => {
    setIsLoading(true)
    setMessage('')

    try {
      const result = await parkingService.checkOut(plateNumber.toUpperCase())
      
      if (result.success) {
        setCheckoutResult(result.data)
        setTicketPreview(null)
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
    setTicketPreview(null)
    setMessage('')
    setPlateNumber('')
  }

  const formatDuration = (checkInTime, checkOutTime) => {
    const duration = new Date(checkOutTime) - new Date(checkInTime)
    const hours = Math.floor(duration / (1000 * 60 * 60))
    const minutes = Math.floor((duration % (1000 * 60 * 60)) / (1000 * 60))
    return `${hours}h ${minutes}m`
  }

  const formatPrice = (price) => {
    if (!price) return 'Rp. 0'
    return `Rp. ${Number(price).toLocaleString('id-ID')}`
  }

  return (
    <div className="checkout-container">
      <h2>Vehicle Check-Out</h2>
      
      {!checkoutResult && !ticketPreview ? (
        <form onSubmit={handlePreviewTicket} className="checkout-form">
          <div className="form-group">
            <label htmlFor="plateNumber">Vehicle Plate Number:</label>
            <input
              type="text"
              id="plateNumber"
              value={plateNumber}
              onChange={(e) => setPlateNumber(e.target.value)}
              placeholder="Enter plate number (e.g., B1234ABC)"
              className="form-input"
              autoFocus
              disabled={isPreviewLoading}
            />
          </div>
          
          <button 
            type="submit" 
            className="submit-btn"
            disabled={isPreviewLoading}
          >
            {isPreviewLoading ? 'Loading Ticket...' : 'Get Ticket Information'}
          </button>
          
          {message && (
            <div className={`message ${message.includes('successfully') ? 'success' : 'error'}`}>
              {message}
            </div>
          )}
        </form>
      ) : ticketPreview ? (
        <div className="ticket-preview">
          <h3>Ticket Information</h3>
          <div className="ticket-info">
            <p><strong>Plate Number:</strong> {ticketPreview.plateNumber}</p>
            <p><strong>Check-in Time:</strong> {new Date(ticketPreview.checkInTime).toLocaleString('id-ID')}</p>
            <p><strong>Duration:</strong> {formatDuration(ticketPreview.checkInTime, new Date())}</p>
            <p><strong>Estimated Price:</strong> {formatPrice(ticketPreview.estimatedPrice)}</p>
            <p><strong>Rate:</strong> Rp. 3,000 per hour</p>
          </div>

          <div className="preview-warning">
            <p><strong>Note:</strong> This is an estimated price based on current time. 
            Final price may vary depending on actual check-out time.</p>
          </div>
          
          <div className="action-buttons">
            <button 
              onClick={handleConfirmCheckout}
              className="confirm-btn"
              disabled={isLoading}
            >
              {isLoading ? 'Processing Check-Out...' : 'Confirm Check-Out'}
            </button>
            
            <button 
              onClick={() => {
                setTicketPreview(null)
                setMessage('')
              }}
              className="cancel-btn"
              disabled={isLoading}
            >
              Cancel
            </button>
          </div>
          
          {message && (
            <div className={`message ${message.includes('successfully') ? 'success' : 'error'}`}>
              {message}
            </div>
          )}
        </div>
      ) : (
        <div className="checkout-display">
          <h3>Check-Out Completed!</h3>
          <div className="ticket-info">
            <p><strong>Plate Number:</strong> {checkoutResult.plateNumber}</p>
            <p><strong>Check-in Time:</strong> {new Date(checkoutResult.checkInTime).toLocaleString('id-ID')}</p>
            <p><strong>Check-out Time:</strong> {new Date(checkoutResult.checkOutTime).toLocaleString('id-ID')}</p>
            <p><strong>Duration:</strong> {formatDuration(checkoutResult.checkInTime, checkoutResult.checkOutTime)}</p>
            <p><strong>Total Price:</strong> {formatPrice(checkoutResult.totalPrice)}</p>
            <p><strong>Rate:</strong> Rp. 3,000 per hour</p>
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
