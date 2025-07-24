## 상품 목록 조회 GET /api/v1/products
sequenceDiagram
    participant User
    participant ProductController
    participant ProductFacade
    participant ProductService
    participant ProductRepository

	User->>ProductController: 상품 목록 조회 요청 (sortBy)
	ProductController->>ProductFacade: 상품 목록 조회 (sortBy)
	ProductService->>ProductRepository: 상품 목록 조회 (sortBy)
	ProductRepository->>ProductService: 상품 목록 조회 결과 반환
	ProductService->>ProductFacade: 상품 목록 반환

## 상품 정보 조회 GET /api/v1/products/{productId}
sequenceDiagram
    participant User
    participant ProductController
    participant ProductFacade
    participant ProductService
    participant BrandService
    participant ProductRepository

	User->>ProductController: 상품 정보 조회 요청 (productId)
	ProductController->>ProductFacade: 상품 정보 조회 (productId)
	ProductService->>ProductRepository: 상품 정보 조회 (productId)
	ProductRepository->>ProductService: 상품 정보 조회 결과 반환
	ProductService->>ProductFacade: 상품 정보 반환
	alt 상품 존재X
		ProductFacade->>ProductController: 404 Not Found
	else 상품 존재O
		BrandService->>ProductFacade: 브랜드 정보 반환
		ProductFacade->>ProductController: (상품 + 브랜드) 정보 반환
	end

## 브랜드 정보 조회 GET /api/v1/brands/{brandId}
sequenceDiagram
    participant User
    participant BrandController
    participant BrandFacade
    participant BrandService
    participant ProductService
    participant BrandRepository

	User->>BrandController: 브랜드 정보 조회 요청 (brandId)
	BrandController->>BrandFacade: 브랜드 정보 조회 (brandId)
	BrandService->>BrandRepository: 브랜드 정보 조회 (brandId)
	BrandRepository->>BrandService: 브랜드 정보 반환
	BrandService->>BrandFacade: 브랜드 정보 반환
	alt 브랜드 존재X
		BrandFacade->>BrandController: 404 Not Found
	else 브랜드 존재O
		BrandFacade->>ProductService: 상품 목록 조회 (brandId)
		ProductService->>BrandFacade: 상품 목록 반환
		BrandFacade->>BrandController: (브랜드 정보 + 상품 목록) 반환
	end

## 상품 좋아요 등록 POST /api/v1/products/{productId}/likes
sequenceDiagram
    participant User
    participant LikeController
    participant LikeFacade
    participant LikeService
    participant UserService
    participant ProductService
    participant LikeRepository

	User->>LikeController: POST /api/v1/products/{productId}/likes
	LikeController->>LikeFacade: 상품 좋아요 등록 (userId, productId)
	LikeFacade->>UserService: 사용자 정보 조회 (userId)
	UserService->>LikeFacade: 사용자 정보 반환
	alt 사용자 존재X
		LikeFacade->>LikeController: 401 Unauthorized
	else 사용자 존재O
		LikeFacade->>ProductService: 상품 정보 조회 (productId)
		ProductService->>LikeFacade: 상품 정보 반환
		alt 상품 존재X
			LikeFacade->>LikeController: 404 Not Found
		else 사용자 존재O
			LikeService->>LikeRepository: 상품 좋아요 조회(userId, productId)
			LikeRepository->>LikeService: 상품 좋아요 정보 반환
			LikeService->>LikeFacade: 상품 좋아요 정보 반환
			alt 좋아요 존재O
				LikeFacade->>LikeController: 409 Conflict
			else 좋아요 존재X
				LikeService->>LikeRepository: 좋아요 저장(userId, productId)
				LikeRepository->>LikeService: 좋아요 저장 결과 반환
				LikeFacade->>ProductService: 상품에 대한 좋아요 수 +1 더한다.
				ProductService->>LikeFacade: 상품의 좋아요 총 갯수를 반환한다.
			end
		end
	end

## 상품 좋아요 취소 DELETE /api/v1/products/{productId}/likes
sequenceDiagram
    participant User
    participant LikeController
    participant LikeFacade
    participant LikeService
    participant UserService
    participant ProductService
    participant LikeRepository

	User->>LikeController: DELETE /api/v1/products/{productId}/likes
	LikeController->>LikeFacade: 상품 좋아요 취소 (userId, productId)
	LikeFacade->>UserService: 사용자 정보 조회 (userId)
	UserService->>LikeFacade: 사용자 정보 반환
	alt 사용자 존재X
		LikeFacade->>LikeController: 401 Unauthorized
	else 사용자 존재O
		LikeFacade->>ProductService: 상품 정보 조회 (productId)
		ProductService->>LikeFacade: 상품 정보 반환
		alt 상품 존재X
			LikeFacade->>LikeController: 404 Not Found
		else 상품 존재O
			LikeService->>LikeRepository: 상품 좋아요 조회(userId, productId)
			LikeRepository->>LikeService: 상품 좋아요 정보 반환
			LikeService->>LikeFacade: 상품 좋아요 정보 반환
			alt 좋아요 존재X
				LikeFacade->>LikeController: 409 Conflict
			else 좋아요 존재O
				LikeService->>LikeRepository: 좋아요 삭제(userId, productId)
				LikeRepository->>LikeService: 좋아요 삭제 결과 반환
				LikeFacade->>ProductService: 상품에 대한 좋아요 수 1을 차감한다.
				ProductService->>LikeFacade: 상품의 좋아요 총 갯수를 반환한다.
			end
		end
	end

## 주문하기 POST /api/v1/orders
sequenceDiagram
    participant User
    participant OrderController
    participant OrderFacade
    participant OrderService
    participant UserService
    participant StockService
    participant OrderRepository
    participant PaymentRepository
    participant PG결제

	User->>OrderController: POST /api/v1/orders
	OrderController->>OrderFacade: 주문하기 (userId, productId, quantity)
	OrderFacade->>UserService: 사용자 정보 조회 (userId)
	UserService->>OrderFacade: 사용자 정보 반환
	alt 사용자 존재X
		OrderFacade->>OrderController: 401 Unauthorized
	else 사용자 존재O
		OrderFacade->>StockService: 재고 정보 조회 (productId)
		StockService->>OrderFacade: 재고 정보 반환
		OrderFacade->>OrderFacade: 상품코드별 주문수량(quantity)과 재고(stock)를 비교한다.
		alt (주문수량 > 재고)
			OrderFacade->>OrderFacade:결제 예정 품목에서 해당 상품을 제외한다.
			OrderFacade->>OrderFacade:총 결제금액 = 총 결제금액 + (price)*(quantity)
		else (재고 >= 주문수량)
			OrderFacade->>OrderFacade:결제 예정 품목에 포함한다.
		OrderFacade->>OrderFacade : 결제 가능한 상품 종류 갯수(n) 확인
		alt n = 0
			OrderFacade->>OrderController: 400 Bad Request(주문 가능한 수량이 없습니다)		
		else n > 0
			OrderFacade->>PointService: 포인트 잔액 조회(userId)
			PointService->>OrderFacade: 포인트 잔액 반환
			alt (총 주문금액 > 포인트 잔액)
				OrderFacade->>OrderController: 400 Bad Request
			else (포인트 잔액 >= 총 주문금액)
				OrderFacade->>OrderService: 주문 등록
				OrderService->>OrderRepository: 주문 저장
				OrderRepository->>OrderService: 주문 정보 반환
				OrderService->>OrderFacade: 주문 정보 반환(orderId)
				OrderFacade->>PG결제: 결제하기(총주문금액)
				alt 결제 실패
					OrderFacade->>OrderController: 500 Internal Server Error
				else 결제 성공
					PG결제->>OrderFacade: 결제 성공 정보 반환
					OrderFacade->>StockService: 재고 차감(productId, quantity)
					OrderFacade->>PointService: 포인트 차감(userId, 총주문금액)
					PointService->>OrderFacade: 포인트 잔액 반환
					OrderFacade->>OrderController: 주문 결과 반환
				end
			end
		end
	end
end

# 유저의 주문 목록 조회 GET /api/v1/orders
sequenceDiagram
    participant User
    participant OrderController
    participant OrderFacade
    participant OrderService
    participant OrderItemService
    participant UserService
    participant OrderRepository
    participant PaymentRepository

	User->>OrderController: GET /api/v1/orders
	OrderController->>OrderFacade: 주문 목록 조회 (userId, productId, quantity)
	OrderFacade->>UserService: 사용자 정보 조회 (userId)
	UserService->>OrderFacade: 사용자 정보 반환
	alt 사용자 존재X
		OrderFacade->>OrderController: 401 Unauthorized
	else 사용자 존재
		OrderFacade->>OrderService: 주문 목록 조회(userId)
		OrderService->>OrderRepository: [주문번호, 주문일시] 목록 조회(userId)
		OrderRepository->>OrderService: 주문 목록 반환
		OrderService->>OrderFacade: 주문번호 목록 반환
		OrderFacade->>OrderItemService: 주문번호별 상품정보[썸네일, 상품명, 수량] 조회(orderId)
		OrderItemService->>OrderFacade: 주문번호별 상품정보 반환
		OrderFacade->>PaymentService: 주문번호별 총 결제금액 조회(orderId)
		PaymentService->>OrderFacade: 주문번호별 총 결제금액 반환
		OrderFacade->>OrderController: 주문 목록[주문일, 상품정보, 총 결제금액] 반환
    end

## 단일 주문 상세 조회 GET /api/v1/orders/{orderId}
sequenceDiagram
    participant User
    participant OrderController
    participant OrderFacade
    participant OrderService
    participant OrderItemService
    participant UserService
    participant OrderRepository
    participant PaymentRepository

	User->>OrderController: GET /api/v1/orders
	OrderController->>OrderFacade: 주문 목록 조회 (userId, productId, quantity)
	OrderFacade->>UserService: 사용자 정보 조회 (userId)
	UserService->>OrderFacade: 사용자 정보 반환
	alt 사용자 존재X
		OrderFacade->>OrderController: 401 Unauthorized
	else 사용자 존재
		OrderFacade->>OrderService: 주문 조회(orderId)
		OrderService->>OrderRepository: [주문일시, 주문상태] 조회(orderId)
		alt 주문은 존재하지만 사용자 정보가 일치하지 않음
			OrderFacade->>OrderController: 403 Forbidden
		else 주문과 사용자 정보가 일치함
			OrderRepository->>OrderService: 주문정보[주문일시, 주문상태] 반환
			OrderService->>OrderFacade: 주문번호 목록 반환
			OrderFacade->>OrderItemService: 주문번호에 속한 상품별 정보[썸네일, 상품명, 수량, 가격, 총 금액] 조회(orderId)
			OrderItemService->>OrderFacade: 주문에 대한 상품정보 반환
			OrderFacade->>PaymentService: 주문에 대한 결제정보[총 결제금액, 결제상태] 조회(orderId)
			PaymentService->>OrderFacade: 주문에 대한 결제정보 반환
			OrderFacade->>OrderController: 주문 목록[주문정보(일시, 상태), 상품정보, 결제정보(총 금액, 상태)] 반환
        end
    end