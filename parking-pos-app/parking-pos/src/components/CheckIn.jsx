import { useState } from 'react'
import { parkingService } from '../api/parkingService'

function CheckIn() {
  const [plateNumber, setPlateNumber] = useState('')
  const [message, setMessage] = useState('')
  const [ticket, setTicket] = useState(null)
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
      const result = await parkingService.checkIn(plateNumber.toUpperCase())
      
      if (result.success) {
        setTicket(result.data)
        setPlateNumber('')
        setMessage('Ticket created successfully!')
      } else {
        setMessage(result.error || 'Failed to create ticket')
      }
    } catch (error) {
      setMessage('An unexpected error occurred')
      console.error('Check-in error:', error)
    } finally {
      setIsLoading(false)
    }
  }

  const handleNewTicket = () => {
    setTicket(null)
    setMessage('')
  }

  return (
    <div className="checkin-container">
      <h2>Vehicle Check-In</h2>
      
      {!ticket ? (
        <form onSubmit={handleSubmit} className="checkin-form">
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
            {isLoading ? 'Creating Ticket...' : 'Create Ticket'}
          </button>
          
          {message && (
            <div className={`message ${message.includes('successfully') ? 'success' : 'error'}`}>
              {message}
            </div>
          )}
        </form>
      ) : (
        <div className="ticket-display">
          <h3>Ticket Created Successfully!</h3>
          <div className="ticket-info">
            <p><strong>Plate Number:</strong> {ticket.plateNumber}</p>
            <p><strong>Check-in Time:</strong> {new Date(ticket.checkInTime).toLocaleString()}</p>
            <p><strong>Ticket ID:</strong> {ticket.id}</p>
          </div>
          <button onClick={handleNewTicket} className="new-ticket-btn">
            Create Another Ticket
          </button>
        </div>
      )}
    </div>
  )
}

export default CheckIn
