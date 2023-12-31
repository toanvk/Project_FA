import { DeleteOutlined, PlusOutlined } from "@ant-design/icons";
import {
  Button,
  Divider,
  Form,
  Input,
  InputNumber,
  InputRef,
  Select,
  Space,
  message,
} from "antd";
import React, { useEffect, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import IconSelectionModal from "../../../components/ModalSelectIcon";
import ShowIcon from "../../../components/ShowIcon";
import UploadSingleImage from "../../../components/SingleUploadImage";
import TextEditer from "../../../components/TextEditer";
import routes from "../../../routes";
import { isLoggedIn } from "../../../services/auth";
import { addBrand, getAllBrands } from "../../../services/brands";
import { createMultipleMetadata } from "../../../services/metadata";
import { getAllMetaDataGroup } from "../../../services/metadataGroup";
import { createProduct } from "../../../services/product";
import {
  addProductCategory,
  getAllProductCategories,
} from "../../../services/productCategory";
import { IBrand } from "../../../types/brand";
import { IMetadata, IMetadataGroup } from "../../../types/metadatagroup";
import { IProduct } from "../../../types/product";
import { IProductCategory } from "../../../types/productCategory";
import { convertToSlug } from "../../../utils/string";
import { IFAQs } from "../../../types/faqs";
import { createMultipleFAQ } from "../../../services/faq";

const { TextArea } = Input;

let metadataId = 0;
let faqId = 0;
const AddProduct: React.FC = () => {
  const navigate = useNavigate();

  const inputRef0 = useRef<InputRef>(null);
  const inputRef1 = useRef<InputRef>(null);

  const [selectIcon, setSelectIcon] = useState(false);

  const [editDataIcon, setEditDataIcon] = useState("");
  const [groupSelected, setGroupSelected] = useState<number>();
  const [categoryName, setCategoryName] = useState("");
  const [categoryDesc, setCategoryDesc] = useState("");
  const [productCategories, setCategories] = useState<IProductCategory[]>();

  const [brandName, setBrandName] = useState("");
  const [brandDesc, setBrandDesc] = useState("");
  const [brandImage, setBrandImage] = useState("");
  const [brands, setBrands] = useState<IBrand[]>();

  const [textEditValue, setTextEditValue] = useState("");
  const [image, setImage] = useState<string | null>(null);

  const [listMetadataGroup, setListMetadataGroup] =
    useState<IMetadataGroup[]>();
  const [metadataInGroup, setMetadataInGroup] = useState<IMetadata[]>([]);
  const [itemMetadataIcon, setItemMetadataIcon] = useState<string[]>([""]);
  const [itemMetadataTitle, setItemMetadataTitle] = useState<string[]>([""]);
  const [itemMetadataContent, setItemMetadateContent] = useState<string[]>([
    "",
  ]);

  const [faqs, setFaqs] = useState<IFAQs[]>([]);
  const [itemFAQTitle, setItemFAQTitle] = useState<string>("");
  const [itemFAQContent, setItemFAQContent] = useState<string>("");

  const formatNumber = (value: any) => {
    if (!value) return "";
    return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
  };

  const parseNumber = (value: any) => {
    return value.replace(/(,*)/g, "");
  };

  const onNameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setCategoryName(event.target.value);
  };

  const addCategory = async (
    e: React.MouseEvent<HTMLButtonElement | HTMLAnchorElement>
  ) => {
    e.preventDefault();
    const newCate: IProductCategory = {
      id: 1,
      name: categoryName,
      description: categoryDesc,
      slug: convertToSlug(categoryName),
    };

    if (newCate) {
      await addProductCategory(newCate)
        .then((data: IProductCategory) => {
          const cateReturn: IProductCategory = data;
          productCategories &&
            setCategories([...productCategories, cateReturn]);
          setCategoryName("");
          setCategoryDesc("");
          message.success("Tạo danh mục thành công!");
        })
        .catch(() => {
          message.error("Lỗi khi tạo danh mục!");
        })
        .finally(() => {
          setTimeout(() => {
            inputRef0.current?.focus();
          }, 0);
        });
    } else {
      message.error("Lỗi khi tạo danh mục!");
    }
  };

  const addNewBrand = async (
    e: React.MouseEvent<HTMLButtonElement | HTMLAnchorElement>
  ) => {
    e.preventDefault();
    const newBrand: IBrand = {
      id: 1,
      name: brandName,
      description: brandDesc,
      image: brandImage,
      slug: convertToSlug(categoryName),
    };

    if (newBrand) {
      await addBrand(newBrand)
        .then((data: IBrand) => {
          const brandReturn: IBrand = data;
          brands && setBrands([...brands, brandReturn]);
          setBrandName("");
          setBrandDesc("");
          message.success("Tạo thương hiệu thành công!");
        })
        .catch(() => {
          message.error("Lỗi khi tạo thương hiệu!");
        })
        .finally(() => {
          setTimeout(() => {
            inputRef1.current?.focus();
          }, 0);
        });
    } else {
      message.error("Lỗi khi tạo danh mục!");
    }
  };

  const addMetadata = async (
    e: React.MouseEvent<HTMLButtonElement | HTMLAnchorElement>,
    group_id: number
  ) => {
    e.preventDefault();

    metadataId += 1;

    const newMetadata: IMetadata = {
      id: metadataId,
      icon: editDataIcon,
      title: itemMetadataTitle[group_id],
      content: itemMetadataContent[group_id],
      laptop_id: 0,
      group_id: group_id,
    };

    if (newMetadata && newMetadata.title && newMetadata.content) {
      metadataInGroup && setMetadataInGroup([...metadataInGroup, newMetadata]);
      setItemMetadataIcon([""]);
      setItemMetadataTitle([""]);
      setItemMetadateContent([""]);
    } else {
      message.error("Lỗi khi tạo danh mục!");
    }
  };

  const removeMetadata = async (id: number) => {
    const metadata = metadataInGroup.filter((item) => item.id !== id);
    setMetadataInGroup(metadata);
  };

  const addFAQ = async (
    e: React.MouseEvent<HTMLButtonElement | HTMLAnchorElement>
  ) => {
    e.preventDefault();

    faqId += 1;

    const faq: IFAQs = {
      id: faqId,
      title: itemFAQTitle,
      content: itemFAQContent,
      laptop_id: 0,
    };

    if (faq && faq.title && faq.content) {
      faqs && setFaqs([...faqs, faq]);
      setItemFAQTitle("");
      setItemFAQContent("");
    } else {
      message.error("Lỗi khi tạo danh mục!");
    }
  };

  const removeFAQ = async (id: number) => {
    const faqsRm = faqs.filter((item) => item.id !== id);
    setFaqs(faqsRm);
  };

  const handleSelectionIcon = (iconName: string) => {
    if (groupSelected) {
      setEditDataIcon(iconName);
      handleIconChange(groupSelected, iconName);
    }
  };

  const handleIconChange = (index: number, value: string) => {
    const newIconValues = [...itemMetadataIcon];
    newIconValues[index] = value;
    setItemMetadataIcon(newIconValues);
  };

  const handleInputTitleChange = (index: number, value: string) => {
    const newInputValues = [...itemMetadataTitle];
    newInputValues[index] = value;
    setItemMetadataTitle(newInputValues);
  };

  const handleInputContentChange = (index: number, value: string) => {
    const newInputValues = [...itemMetadataContent];
    newInputValues[index] = value;
    setItemMetadateContent(newInputValues);
  };

  const onFinish = async (values: any) => {
    const username = localStorage.getItem("username");

    if (isLoggedIn() && username) {
      const product: IProduct = {
        id: 0,
        userName: username,
        title: values.title,
        metaTitle: values.metaTitle,
        slug: convertToSlug(values.title),
        summary: textEditValue,
        image: image,
        sku: "", // xử lý backend
        price: values.price,
        discount: values.discount,
        quantity: values.quantity,
        categoryId: values.categoryId,
        brandId: values.brandId,
      };

      await createProduct(product)
        .then((data: number) => {
          const laptopId = data;
          createMultipleMetadata(metadataInGroup, laptopId);
          createMultipleFAQ(faqs, laptopId);
          message.success("Thành công!");
          navigate(routes.ADMIN_PRODUCTS);
        })
        .catch(() => {
          message.error("Thất bại!");
        });
    } else {
      message.success("Hết hạn, đăng nhập lại!");
      navigate(routes.LOGIN);
    }
  };

  const onFinishFailed = (errorInfo: any) => {
    console.log("Failed:", errorInfo);
  };

  useEffect(() => {
    getAllProductCategories().then((data: IProductCategory[]) => {
      setCategories(data);
    });

    getAllBrands().then((data: IBrand[]) => {
      setBrands(data);
    });

    getAllMetaDataGroup().then((data: IMetadataGroup[]) => {
      setListMetadataGroup(data);
    });
  }, []);

  return (
    <div>
      <Form
        name="newBlogForm"
        layout="vertical"
        labelCol={{ span: 8 }}
        initialValues={{
          title: "111123",
          categoryId: 2,
          brandId: 2,
          price: 111,
          discount: 111,
          quantity: 111,
          metaTitle: "hahaha",
        }}
        onFinish={onFinish}
        onFinishFailed={onFinishFailed}
        autoComplete="off"
      >
        <Form.Item
          label="Tiêu đề"
          name="title"
          rules={[{ required: true, message: "Hãy nhập tiêu đề!" }]}
        >
          <Input size="large" placeholder="Tiêu đề nhãn hàng!" />
        </Form.Item>

        <Form.Item
          label="Danh mục"
          name="categoryId"
          rules={[{ required: true, message: "Chọn danh mục!" }]}
        >
          <Select
            placeholder="Chọn danh mục hoặc tạo mới!"
            size="large"
            dropdownRender={(menu) => (
              <>
                {menu}
                <Divider style={{ margin: "8px 0" }} />
                <Space style={{ padding: "0 8px 4px" }}>
                  <div className="flex flex-col">
                    <Input
                      placeholder="Nhập tên danh mục mới!"
                      ref={inputRef0}
                      value={categoryName}
                      onChange={onNameChange}
                    />
                    <Divider style={{ margin: "8px 0" }} />
                    <TextArea
                      placeholder="Mô tả về danh mục"
                      value={categoryDesc}
                      onChange={(e) => setCategoryDesc(e.target.value)}
                      autoSize={{ minRows: 3, maxRows: 5 }}
                    />
                    <Divider style={{ margin: "8px 0" }} />
                    <Button
                      danger
                      icon={<PlusOutlined />}
                      onClick={addCategory}
                    >
                      Thêm danh mục
                    </Button>
                  </div>
                </Space>
              </>
            )}
            options={
              productCategories &&
              productCategories.map((item: IProductCategory) => ({
                label: item.name,
                value: item.id,
              }))
            }
          />
        </Form.Item>

        <Form.Item
          label="Thương hiệu"
          name="brandId"
          rules={[{ required: true, message: "Chọn danh thương hiệu!" }]}
        >
          <Select
            placeholder="Chọn thương hiệu hoặc tạo mới!"
            size="large"
            dropdownRender={(menu) => (
              <>
                {menu}
                <Divider style={{ margin: "8px 0" }} />
                <Space style={{ padding: "0 8px 4px" }}>
                  <div className="flex flex-col">
                    <Input
                      placeholder="Nhập tên thương hiệu mới!"
                      ref={inputRef1}
                      value={brandName}
                      onChange={(e) => setBrandName(e.target.value)}
                    />
                    <Divider style={{ margin: "8px 0" }} />
                    <TextArea
                      placeholder="Mô tả thương hiệu"
                      value={brandDesc}
                      onChange={(e) => setBrandDesc(e.target.value)}
                      autoSize={{ minRows: 3, maxRows: 5 }}
                    />
                    <Divider style={{ margin: "8px 0" }} />
                    <UploadSingleImage
                      valueProps={brandImage}
                      setValueProps={setBrandImage}
                    />
                    <Divider style={{ margin: "8px 0" }} />
                    <Button
                      danger
                      icon={<PlusOutlined />}
                      onClick={addNewBrand}
                    >
                      Thêm brand
                    </Button>
                  </div>
                </Space>
              </>
            )}
            options={
              brands &&
              brands.map((item: IBrand) => ({
                label: item.name,
                value: item.id,
              }))
            }
          />
        </Form.Item>

        <div className="flex flex-col md:flex-row items-center justify-around">
          <Form.Item
            label="Giá sản phẩm"
            name="price"
            rules={[{ required: true, message: "Hãy nhập giá!" }]}
          >
            <InputNumber
              style={{ minWidth: 300 }}
              size="large"
              formatter={formatNumber}
              parser={parseNumber}
            />
          </Form.Item>

          <Form.Item
            label="Giảm giá"
            name="discount"
            rules={[{ required: true, message: "Hãy nhập discount!" }]}
          >
            <InputNumber
              style={{ minWidth: 300 }}
              size="large"
              min={0}
              max={100}
            />
          </Form.Item>

          <Form.Item
            label="Số lượng"
            name="quantity"
            rules={[{ required: true, message: "Hãy nhập số lượng!" }]}
          >
            <InputNumber
              style={{ minWidth: 300 }}
              size="large"
              formatter={formatNumber}
              parser={parseNumber}
            />
          </Form.Item>
        </div>

        <Form.Item
          label="Meta title"
          name="metaTitle"
          rules={[{ required: true, message: "Hãy nhập mô tả ngắn!" }]}
        >
          <TextArea
            size="large"
            placeholder="Mô tả về thương hiệu"
            autoSize={{ minRows: 3, maxRows: 5 }}
          />
        </Form.Item>

        <Form.Item label="Ảnh đại diện">
          <UploadSingleImage valueProps={image} setValueProps={setImage} />
        </Form.Item>

        <TextEditer
          valueProps={textEditValue}
          setValueProps={setTextEditValue}
        />

        <Divider dashed />
        <div className="flex space-x-2">
          <div className="flex-1">
            {listMetadataGroup &&
              listMetadataGroup?.map((metadataGroup: IMetadataGroup) => (
                <div
                  key={metadataGroup.id}
                  id={metadataGroup.id.toFixed()}
                  className="flex flex-col"
                >
                  <div className="font-bold text-lg">{metadataGroup.name}</div>

                  {metadataInGroup?.map(
                    (metadata: IMetadata) =>
                      metadataGroup.id === metadata.group_id && (
                        <div
                          key={metadata.id}
                          id={metadata.id.toFixed()}
                          className="flex flex-col space-y-2 max-w-md mb-2"
                        >
                          <div className="flex space-x-2 items-center">
                            {metadata.icon && metadata.icon !== "" && (
                              <ShowIcon name={metadata.icon} size={30} />
                            )}
                            <div>
                              <b>{metadata.title} : </b>
                              {metadata.content}
                            </div>

                            <Button
                              danger
                              icon={<DeleteOutlined />}
                              onClick={() => {
                                removeMetadata(metadata.id);
                              }}
                            >
                              Xóa
                            </Button>
                          </div>
                        </div>
                      )
                  )}

                  <div className="flex flex-col space-y-2 max-w-md">
                    <div className="flex space-x-2">
                      <ShowIcon
                        name={
                          itemMetadataIcon[metadataGroup.id] || "FcAddImage"
                        }
                        size={30}
                        onClick={() => {
                          setSelectIcon(true);
                          setGroupSelected(metadataGroup.id);
                        }}
                        className="cursor-pointer"
                      />

                      <div className="flex space-x-2">
                        <Input
                          placeholder={`Nhập ${metadataGroup.name.toLowerCase()} mới`}
                          value={itemMetadataTitle[metadataGroup.id]}
                          onChange={(e) =>
                            handleInputTitleChange(
                              metadataGroup.id,
                              e.target.value
                            )
                          }
                        />
                        <Input
                          placeholder={`Nhập nội dung ${metadataGroup.name.toLowerCase()} mới`}
                          value={itemMetadataContent[metadataGroup.id]}
                          onChange={(e) =>
                            handleInputContentChange(
                              metadataGroup.id,
                              e.target.value
                            )
                          }
                        />
                      </div>
                    </div>
                    <Button
                      danger
                      icon={<PlusOutlined />}
                      onClick={(e) => {
                        addMetadata(e, metadataGroup.id);
                      }}
                    >
                      Thêm
                    </Button>
                  </div>
                </div>
              ))}
          </div>
          <div className="flex-1">
            <div className="font-bold text-lg">FAQs</div>
            {faqs.length > 0 &&
              faqs.map((faq) => (
                <div key={faq.id}>
                  {faq.title} : {faq.content}
                  <Button
                    danger
                    icon={<DeleteOutlined />}
                    onClick={() => {
                      removeFAQ(faq.id);
                    }}
                  >
                    Xóa
                  </Button>
                </div>
              ))}
            <div className="flex space-x-2">
              <div className="flex flex-col space-y-2 w-full">
                <Input
                  placeholder={`Nhập title faq mới`}
                  value={itemFAQTitle}
                  onChange={(e) => setItemFAQTitle(e.target.value)}
                />
                <TextArea
                  placeholder={`Nhập nội dung faq mới`}
                  value={itemFAQContent}
                  onChange={(e) => setItemFAQContent(e.target.value)}
                  autoSize={{ minRows: 3, maxRows: 5 }}
                />
              </div>
            </div>
            <Button
              className="mt-2"
              danger
              icon={<PlusOutlined />}
              onClick={(e) => {
                addFAQ(e);
              }}
            >
              Thêm
            </Button>
          </div>
        </div>
        <IconSelectionModal
          visible={selectIcon}
          onClose={() => {
            setSelectIcon(false);
          }}
          onSelectIcon={handleSelectionIcon}
        />

        <Button
          type="primary"
          className="bg-[#CD1818] hover:bg-[#6d6d6d] my-3"
          htmlType="submit"
        >
          Thêm mới
        </Button>
      </Form>
    </div>
  );
};

export default AddProduct;
