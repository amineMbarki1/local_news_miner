import {
  Card,
  CardMedia,
  CardContent,
  Typography,
  CardActions,
  Button,
} from "@mui/material";

const ArticleCard = ({ currentArticle }) => {
  let body;
  console.log(currentArticle.body);
  const doc = new DOMParser().parseFromString(currentArticle.body, "text/html");
  body = [...doc.querySelectorAll("p")].find((p) => {
    return !!p.innerText;
  }).innerText;
  console.log(!!currentArticle.body);

  return (
    <div>
      {currentArticle ? (
        <Card>
          <CardMedia
            style={{ height: 0, paddingTop: "56.25%" }}
            image={currentArticle.featuredImg}
            title={currentArticle.title}
          />
          <CardContent style={{ padding: "10" }}>
            <Typography
              gutterBottom
              variant="headline"
              component="h4"
              color={"darkslategray"}
              textAlign={"initial"}
            >
              {currentArticle.title}
            </Typography>
            <Typography
              component="p"
              color={"GrayText"}
              fontSize={"small"}
              textAlign={"initial"}
            >
              {body}
            </Typography>
          </CardContent>
          <CardActions>
            <Button size="small" color="primary" href={"#"} target="_blank">
              Go To Article
            </Button>
          </CardActions>
        </Card>
      ) : null}
    </div>
  );
};

export default ArticleCard;
