import { Grid, TextField } from "@mui/material";
import ArticleCard from "./ArticleCard";

const ArticlesList = ({ articles }) => {
  return (
    <div>
      {articles ? (
        <div>
          {/* <TextField
            style={{ padding: 24 }}
            id="searchInput"
            placeholder="Search for Courses"
            margin="normal"
            onChange={(value) => {
              console.log(value);
            }}
          /> */}
          <Grid container spacing={1} style={{ padding: 1 }}>
            {articles.map((currentArticle) => (
              <Grid key={currentArticle.title} item lg={4}>
                <ArticleCard currentArticle={currentArticle} />
              </Grid>
            ))}
          </Grid>
        </div>
      ) : (
        "No found found"
      )}
    </div>
  );
};

export default ArticlesList;
