/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    extend: {
      colors: {
        primary: '#0866ff',
        text: '#050505',
        icon: '#65676b',
        hover: '#e3e5e8'
      }
    },
  },
  plugins: [],
}

