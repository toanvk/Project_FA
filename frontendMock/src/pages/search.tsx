import { useParams } from "react-router-dom";
import { useEffect } from "react";

const SearchPage = () => {
  const param: any = useParams();
  console.log(param.name);

  useEffect(() => {});

  return <>okok</>;
};

export default SearchPage;
