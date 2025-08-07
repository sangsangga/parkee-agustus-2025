import { useState } from 'react'

function CheckIn({ onAddTicket }) {
  const [plateNumber, setPlateNumber] = useState('')
  const [message, setMessage] = useState('')
  const [ticket, setTicket] = useState(null)

  const handleSubmit = (e) => {
    e.preventDefault()
    
    if (!plateNumber.trim()) {
      setMessage('Please enter a valid plate number')
      return
    }

    const newTicket = onAddTicket(plateNumber.toUpperCase())
    setTicket(newTicket)
    setPlateNumber('')
    setMessage('Ticket created successfully!')
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
            />
          </div>
          
          <button type="submit" className="submit-btn">
            Create Ticket
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
            <p><strong>Check-in Time:</strong> {ticket.checkInTime.toLocaleString()}</p>
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
