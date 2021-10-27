import React, { useState, useEffect } from 'react';
import axios from 'axios'
import './App.css';

function App() {
  // 요청받은 정보를 담아줄 변수 선언
  const [ user, setUser ] = useState('');

  // 변수 초기화
  function callback(str) {
    setUser(str);
  }

  // 첫 번째 렌더링을 마친 후 실행
  useEffect(
      () => {
        axios({
            url: '/api/members/one',
            method: 'GET'
        }).then((res) => {
            callback(res.data);
        })
      }, []
  );

  return (
      <div className="App">
          <header className="App-header">
              <h1>{user.id}</h1>
              <h1>{user.passwd}</h1>
              <h1>{user.name}</h1>
              <h1>{user.email}</h1>
          </header>
      </div>
  );
}

export default App;