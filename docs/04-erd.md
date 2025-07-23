erDiagram
    USER {
        bigint id PK
        varchar user_id
        varchar email
        varchar name
        char gender
        varchar birth_date
        datetime created_at
        datetime updated_at
    }

	BRAND {
		bigint id PK
		varchar name
		varchar description
		varchar thumbnail
		varchar homepage_url
		datetime created_at
		datetime updated_at
	}
	
	PRODUCT {
		bigint id PK
		bigint ref_brand_id FK
		varchar name
		decimal price
		varchar thumbnail
		datetime created_at
		datetime updated_at
	}
	
	STOCK {
		bigint id
		bigint ref_product_id FK
		int inbound
		int outbound
		int available
		datetime created_at
	}
	
	LIKE {
		bigint ref_product_id FK
		bigint ref_user_id FK
		datetime created_at
	}
	
	POINT {
		bigint id PK
		bigint ref_user_id FK
		decimal inbound
		decimal outbound
		decimal balance
		datetime createdAt
	}
	
	ORDER {
		bigint id PK
		bigint ref_user_id FK
		datetime created_at
	}
	
	ORDER_ITEM {
		bigint id PK
		bigint ref_order_id FK
        varchar product_name
		varchar thumbnail
		decimal price
		int quantity
		decimal amount
		varchar status
		datetime created_at
	}
	
	PAYMENT {
		bigint id PK
		bigint ref_user_id FK
		bigint ref_order_id FK
		decimal amount
		varchar status
		datetime created_at
		datetime updated_at
	}