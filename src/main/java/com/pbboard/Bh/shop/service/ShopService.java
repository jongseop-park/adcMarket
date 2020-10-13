package com.pbboard.Bh.shop.service;

import com.pbboard.Bh.shop.domain.ShopPageMaker;
import com.pbboard.domain.ShopVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopService {

    public List<ShopVO> findMainCat();  //메인카테고리

    public List<ShopVO> findSubCat(String mainCat); //서브 카테고리

    public List<ShopVO> findColor();    //옷의 색상

    public List<ShopVO> findSize();     //옷의 사이즈

    public List<ShopVO> findProduct(ShopPageMaker shopPageMaker);  //제품 정보

    public List<ShopVO> findDiscount(); //제품 할인

    public List<ShopVO> findNewProduct();//신 제품 확인

    public List<ShopVO> findOutOfStock();//품절 확인

    public ShopVO productDetail(Long productDetail);    //제품 정보

    public int reviewCount(Long productSeq);    //제품 리뷰 총 수

    public int productCount(ShopPageMaker shopPageMaker);  //등록된 제품 수

    public int reviewStar(Long productSeq); //제품 별점

    public String[][] subCategory();    //각 카테고리 모아서 가져가기 위해 사용

    public String productList(List<ShopVO> productInfo);    //현재 화면에 나오는 제품의 seq 모음
}