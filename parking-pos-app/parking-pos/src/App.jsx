import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom'
import { useState } from 'react'
import './App.css'
import CheckIn from './components/CheckIn'
import CheckOut from './components/CheckOut'

function App() {
  const [tickets, setTickets] = useState([])

  const addTicket = (plateNumber) => {
    const newTicket = {
      id: Date.now(),
      plateNumber,
      checkInTime: new Date(),
      checkOutTime: null,
      totalPrice: 0
    }
    setTickets([...tickets, newTicket])
    return newTicket
  }

  const checkoutTicket = (plateNumber) => {
    const ticketIndex = tickets.findIndex(ticket => ticket.plateNumber === plateNumber)
    if (ticketIndex === -1) return null

    const updatedTickets = [...tickets]
    const checkOutTime = new Date()
    const checkInTime = new Date(updatedTickets[ticketIndex].checkInTime)
    const durationInHours = (checkOutTime - checkInTime) / (1000 * 60 * 60)
    const totalPrice = Math.ceil(durationInHours) * 5 // $5 per hour

    updatedTickets[ticketIndex] = {
      ...updatedTickets[ticketIndex],
      checkOutTime,
      totalPrice
    }

    setTickets(updatedTickets)
    return updatedTickets[ticketIndex]
  }

  const getTicketByPlateNumber = (plateNumber) => {
    return tickets.find(ticket => ticket.plateNumber === plateNumber)
  }

  return (
    <Router>
      <div className="app">
        <header className="app-header">
          <h1>Parking POS System</h1>
          <nav>
            <Link to="/" className="nav-link">Check In</Link>
            <Link to="/checkout" className="nav-link">Check Out</Link>
          </nav>
        </header>

        <main className="app-main">
          <Routes>
            <Route 
              path="/" 
              element={<CheckIn onAddTicket={addTicket} />} 
            />
            <Route 
              path="/checkout" 
              element={
                <CheckOut 
                  onCheckoutTicket={checkoutTicket}
                  getTicketByPlateNumber={getTicketByPlateNumber}
                />
              } 
            />
          </Routes>
        </main>
      </div>
    </Router>
  )
}

export default App
