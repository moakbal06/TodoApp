# Domain Model

TodoApp uygulamasının alan modeli (domain model) tanımları.

## Todo Entity

**Dosya:** `src/main/java/com/example/todoapp/model/Todo.java`

**Alanlar:**
- `id: String` — MongoDB/Couchbase document ID (otomatik üretilir)
- `userId: String` — Todo'nun sahibi kullanıcı ID'si (required, @NotBlank)
- `description: String` — Todo açıklaması (required, @NotBlank)
- `completed: Boolean` — Tamamlanma durumu (varsayılan: false, null safe)
- `date: LocalDateTime` — Oluşturulma zamanı (otomatik set edilir, immutable)

**JSON Format:**
- `date` alanı Jackson `@JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")` ile formatlanır

**İş Kuralları:**
- Todo oluşturulurken `completed` otomatik `false` olarak set edilir
- Todo oluşturulurken `date` otomatik `LocalDateTime.now()` ile set edilir
- Todo düzenlenirken `date` alanı değiştirilemez (immutable creation timestamp)

## User Entity

**Dosya:** `src/main/java/com/example/todoapp/model/User.java`

**Alanlar:**
- `id: String` — MongoDB/Couchbase document ID
- `name: String` — Kullanıcı adı (max 20 karakter)
- `mail: String` — Email adresi (max 50 karakter, unique)
- `password: String` — Hash'lenmiş parola

**Validation:**
- `userId` ve `description` alanları zorunludur (@NotBlank)
- `editTodo` operasyonunda `id` null check yapılır
- `completed` alanı null ise varsayılan `false` değeri kullanılır

## Changelog

- 2026-10-07 [OTO-2] § Mesajlar — Validation ve exception mesajları Türkçeleştirildi (NotBlank mesajları, TodoNotFoundException mesajları)
- 2026-10-06 [OTO-2] § Todo Entity — `date: LocalDateTime` alanı eklendi, oluşturma zamanı otomatik set ediliyor
- 2026-10-06 [OTO-2-review] § Validation — `userId` ve `description` alanlarına @NotBlank, editTodo'ya null check ve completed null safety eklendi
