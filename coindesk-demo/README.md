# coindesk-demo

一個基於 Spring Boot + Maven + H2 的範例專案，實作下列功能：

- 幣別資料表 CRUD（code ↔ 中文名稱）
- 呼叫 Coindesk 原始 API (`/api/coindesk/raw`)
- 轉換 Coindesk 資料並輸出自訂格式 API (`/api/coindesk`)

---

## 技術棧

- Java 8
- Spring Boot 2.7.0
- Spring Data JPA
- H2（in-memory）
- RestTemplate
- JUnit 5 + Mockito + Spring Test (MockMvc, MockRestServiceServer)

---

## 快速上手

1. **Clone 專案**
   ```bash
   git clone https://github.com/你的帳號/coindesk-demo.git
   cd coindesk-demo
## H2 Console

- **URL**：<http://localhost:8080/h2-console>
- **JDBC URL**：`jdbc:h2:mem:coindeskdb`
- **Username**：`sa`
- **Password**：空白

可在 Console 裡檢視 `currency` 資料表及初始測試資料（USD/GBP/EUR）。

---

## API 文件

### 幣別 CRUD

| 方法    | 路徑                         | Request Body                             | 功能                      |
| ------- | ---------------------------- | ----------------------------------------- | ------------------------- |
| **GET**    | `/api/currencies`           | —                                         | 取得所有幣別               |
| **GET**    | `/api/currencies/{code}`    | —                                         | 取得單一幣別 (依 code)      |
| **POST**   | `/api/currencies`           | `{ "code":"JPY", "chineseName":"日圓" }`  | 新增一筆幣別               |
| **PUT**    | `/api/currencies/{code}`    | `{ "chineseName":"日本圓" }`               | 更新該 code 的中文名稱     |
| **DELETE** | `/api/currencies/{code}`    | —                                         | 刪除該 code 的幣別          |

#### 範例：用 cURL 新增 JPY（日圓）

## Coindesk 原始 & 轉換 API

| 方法  | 路徑                   | 回傳格式                     |
| ----- | ---------------------- | ---------------------------- |
| GET   | `/api/coindesk/raw`    | Coindesk 原始 JSON           |
| GET   | `/api/coindesk`        | 轉換後 JSON，範例如下：       |

#### 轉換後範例 JSON
```json
{
  "updateTime": "yyyy/MM/dd HH:mm:ss",
  "currencies": [ … ]
}
