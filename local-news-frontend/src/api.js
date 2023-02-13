import axios from "axios";

const NEWS_FEED_URL = "http://localhost:8082/news-feed";

export default async function getArticles() {
  return await axios.get(NEWS_FEED_URL);
}
