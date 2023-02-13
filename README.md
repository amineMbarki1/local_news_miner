# Local News Miner
An application that scrapes local news articles from Tunisian news websites and aggregate them into a single database, the articles saved are then exposed as JSON for clienls.<br>

The Server will notify the clients when  new content was scraped and added to the database, the client can subscribe to these notifications through SSE (Server sent events)

### **Technologies:**
  * **News scraper module**: Java / Jsoup
  * **Backend**: Sprinqg boot and Mysql
  * **Frontend**:  React 

### **Sequence diagram:**

![Sequence diagram](./sequence.png?raw=true)

### **Class diagram for the news scraper module:**

![Class diagram for the news scraper module](./class.png?raw=true)
