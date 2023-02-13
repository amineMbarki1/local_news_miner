import "./App.css";
import getArticles from "./api";

import { useState, useEffect } from "react";
import ArticlesList from "./ArticlesList";
import { Container } from "@mui/system";
import { Backdrop, CircularProgress } from "@mui/material";
function App() {
  const [newsArticles, setNewsArticle] = useState([]);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);
  const [contentDidUpdate, setContentDidUpdate] = useState(0);

  const fetchArticles = async () => {
    try {
      setLoading(true);
      const { data } = await getArticles();
      setNewsArticle(data);
    } catch (error) {
      console.log(error);
      setError(new Error(error?.message));
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchArticles();
    const eventSource = new EventSource("http://localhost:8082/sse");
    eventSource.addEventListener("Amine", (event) => {
      console.log("Backend Scraped new articles");
      setContentDidUpdate((prev) => prev + 1);
    });
  }, []);

  useEffect(() => {
    if (contentDidUpdate > 0) fetchArticles();
  }, [contentDidUpdate]);

  return (
    <div className="App">
      <Backdrop
        sx={{ color: "#fff", zIndex: (theme) => theme.zIndex.drawer + 1 }}
        open={loading}
      >
        <CircularProgress color="inherit" />
      </Backdrop>
      <h1>{contentDidUpdate}</h1>
      <Container maxWidth="md">
        <ArticlesList articles={newsArticles} />
      </Container>
    </div>
  );
}

export default App;
