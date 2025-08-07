import { useState } from 'react'

function CheckOut({ onCheckoutTicket, getTicketByPlateNumber }) {
  const [plateNumber, setPlateNumber] = useState('')
  const [message, setMessage] = useState('')
  const [checkoutResult, setCheckoutResult] = useState(null)

  const handleSubmit = (e) => {
    e.preventDefault()
    
    if (!plateNumber.trim()) {
      setMessage('Please enter a valid plate number')
      return
    }

    const ticket = getTicketByPlateNumber(plateNumber.toUpperCase())
    
    if (!ticket) {
      setMessage('No ticket found for this plate number')
      return
    }

    if (ticket.checkOutTime) {
      setMessage('This ticket has already been checked out')
      return
    }

    const checkoutTicket = onCheckoutTicket(plateNumber.toUpperCase())
    setCheckoutResult(checkoutTicket)
    setPlateNumber('')
    setMessage('Checkout completed successfully!')
  }

  const handleNewCheckout = () => {
    setCheckoutResult(null)
    setMessage('')
  }

  const formatDuration = (checkInTime, checkOutTime) => {
    const duration = checkOutTime - new Date(checkInTime)
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
            />
          </div>
          
          <button type="submit" className="submit-btn">
            Process Check-Out
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
            <p><strong>Check-out Time:</strong> {checkoutResult.checkOutTime.toLocaleString()}</p>
            <p><strong>Duration:</strong> {formatDuration(checkoutResult.checkInTime, checkoutResult.checkOutTime)}</p>
            <p><strong>Total Price:</strong> ${checkoutResult.totalPrice.toFixed(2)}</p>
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
