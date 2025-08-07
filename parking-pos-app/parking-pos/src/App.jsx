import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom'
import { useState } from 'react'
import './App.css'
import CheckIn from './components/CheckIn'
import CheckOut from './components/CheckOut'

function App() {
  const [activeTab, setActiveTab] = useState('checkin')

  const handleTabClick = (tab) => {
    setActiveTab(tab)
  }

  return (
    <Router>
      <div className="app">
        <header className="app-header">
          <h1>Parking POS System</h1>
          <nav>
            <Link to="/" className={`nav-link ${activeTab === 'checkin' ? 'active' : ''}`} onClick={() => handleTabClick('checkin')}>Check In</Link>
            <Link to="/checkout" className={`nav-link ${activeTab === 'checkout' ? 'active' : ''}`} onClick={() => handleTabClick('checkout')}>Check Out</Link>
          </nav>
        </header>

        <main className="app-main">
          <Routes>
            <Route 
              path="/" 
              element={<CheckIn />} 
            />
            <Route 
              path="/checkout" 
              element={<CheckOut />} 
            />
          </Routes>
        </main>
      </div>
    </Router>
  )
}

export default App
