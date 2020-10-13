package com.pbboard.Bh.shop.mapper;

import com.pbboard.Bh.shop.domain.ShopPageMaker;
import com.pbboard.domain.ShopVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ShopMapper {

    public List<ShopVO> findMainCat();  //메인 카테고리

    public List<ShopVO> findSubCat(@Param("MAINCAT") String mainCat);   //서브 카테고리

    public List<ShopVO> findColor();    //제품 색상표??

    public List<ShopVO> findSize();     //제품 사이즈표??

    public List<ShopVO> findProduct(ShopPageMaker shopPageMaker);  //제품 정보

    public List<ShopVO> findDiscount(); //제품 할인

    public List<ShopVO> findNewProduct(); //신 제품 확인

    public List<ShopVO> findOutOfStock(); //품절 확인

    public ShopVO productDetail(@Param("productSeq") Long productSeq);   //제품 정보

    public int productCount(ShopPageMaker shopPageMaker);          //등록된 제품 수

    public int reviewCount(@Param("productSeq") Long productSeq);   //제품 리뷰 총 수

    public int reviewStar(@Param("productSeq") Long productSeq);   //제품 별점

    public List<ShopVO> findProductSpec(@Param("productSeq") Long productSeq);    //제품 스팩
}