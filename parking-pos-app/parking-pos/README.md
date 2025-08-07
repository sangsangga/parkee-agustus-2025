# Parking POS System

A modern, responsive Parking Point of Sale (POS) system built with React and Vite. This application provides a complete solution for managing vehicle check-ins and check-outs with automatic pricing calculation.

## Features

### 🚗 Check-In Page
- Input vehicle plate number to create parking tickets
- Automatic timestamp recording for check-in time
- Unique ticket ID generation
- Real-time validation and feedback
- Clean, modern UI with smooth animations

### 🚙 Check-Out Page
- Input vehicle plate number to retrieve tickets
- Automatic calculation of parking duration
- Pricing calculation ($5.00 per hour)
- Display of complete ticket information:
  - Check-in time
  - Check-out time
  - Duration (hours and minutes)
  - Total price
- Prevention of duplicate check-outs

### 🎨 User Interface
- Modern, responsive design with gradient backgrounds
- Glassmorphism effects with backdrop blur
- Smooth hover animations and transitions
- Mobile-friendly responsive layout
- Intuitive navigation between pages

## Technology Stack

- **React 19** - Modern React with hooks
- **React Router DOM** - Client-side routing
- **Vite** - Fast build tool and development server
- **CSS3** - Modern styling with gradients and animations

## Getting Started

### Prerequisites
- Node.js (version 18 or higher)
- npm or yarn

### Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd parking-pos
```

2. Install dependencies:
```bash
npm install
```

3. Start the development server:
```bash
npm run dev
```

4. Open your browser and navigate to `http://localhost:5173`

## Usage

### Check-In Process
1. Navigate to the Check-In page (default route)
2. Enter the vehicle plate number (e.g., "ABC123")
3. Click "Create Ticket" to generate a parking ticket
4. View the ticket details including check-in time and ticket ID
5. Click "Create Another Ticket" to process another vehicle

### Check-Out Process
1. Navigate to the Check-Out page
2. Enter the vehicle plate number
3. Click "Process Check-Out" to complete the transaction
4. View the complete ticket information including:
   - Check-in and check-out times
   - Duration of stay
   - Total price calculation
5. Click "Process Another Check-Out" to handle another vehicle

## Pricing Structure

- **Rate**: $5.00 per hour
- **Calculation**: Rounded up to the nearest hour
- **Example**: 2 hours 30 minutes = 3 hours = $15.00

## Development

### Available Scripts

- `npm run dev` - Start development server
- `npm run build` - Build for production
- `npm run preview` - Preview production build
- `npm run lint` - Run ESLint

### Project Structure

```
src/
├── components/
│   ├── CheckIn.jsx      # Check-in page component
│   └── CheckOut.jsx     # Check-out page component
├── App.jsx              # Main app component with routing
├── App.css              # Application styles
├── main.jsx             # Application entry point
└── index.css            # Global styles
```

## Features in Detail

### Data Management
- In-memory ticket storage using React state
- Automatic ticket ID generation using timestamps
- Plate number validation and normalization
- Duplicate check-out prevention

### User Experience
- Real-time form validation
- Success and error message display
- Smooth transitions between states
- Responsive design for all screen sizes
- Keyboard navigation support

### Security & Validation
- Input sanitization for plate numbers
- Duplicate check-out prevention
- Non-existent ticket handling
- Proper error messaging

## Future Enhancements

- Database integration for persistent storage
- User authentication and authorization
- Multiple parking lot support
- Payment processing integration
- Receipt printing functionality
- Analytics and reporting dashboard
- API endpoints for external integrations

## License

This project is licensed under the MIT License.
