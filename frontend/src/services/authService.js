// authService.js
const API_URL = "http://localhost:8080/api/auth";  // Ajusta la URL de tu backend según sea necesario

// Metodo para hacer login y almacenar el token JWT
const login = async (username, password) => {
  const response = await fetch(`${API_URL}/login`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ username, password }),
  });

  const data = await response.json();

  if (response.ok) {
    // Almacenar el token JWT en localStorage
    localStorage.setItem("jwtToken", data.jwt);
    return data;
  } else {
    throw new Error("Login failed");
  }
};

// Metodo para cerrar sesión (remover el token del localStorage)
const logout = () => {
  localStorage.removeItem("jwtToken");
};

// Metodo para obtener datos protegidos usando el token JWT
const fetchProtectedData = async () => {
  const token = localStorage.getItem("jwtToken");

  if (!token) {
    throw new Error("No token found, user not authenticated");
  }

  const response = await fetch("http://localhost:8080/protected-endpoint", {
    method: "GET",
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  if (response.ok) {
    const data = await response.json();
    return data;
  } else {
    throw new Error("Failed to fetch protected data");
  }
};

export { login, logout, fetchProtectedData };
