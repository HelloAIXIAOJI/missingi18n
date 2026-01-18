# Missing i18n

**Missing i18n** 是一个为 Minecraft 翻译者、模组包作者和开发者设计的辅助模组。它能自动检测并记录游戏中所有缺失的语言本地化键（Translation Keys），并将其分类保存，极大地方便了汉化和排查工作。

## 结果输出

捕获到的缺失键将保存在游戏根目录下的 `missingi18n/` 文件夹内：
- `missingi18n/<modid>/<lang>.txt`：按模组 ID 分类的缺失键。
- `missingi18n/all/<lang>.txt`：所有模组缺失键的汇总。

格式示例：`item.example_mod.example_item|`

## 开发者 API

如果你是模组开发者，可以通过以下方式与 Missing i18n 联动：

### 使用 API 手动记录
```java
import missing.i18n.api.Missingi18nAPI;

// 手动触发记录一个缺失键
Missingi18nAPI.recordMissingKey("my.custom.missing.key");

// 检查某个键是否已被记录
boolean recorded = Missingi18nAPI.isRecorded("my.key", "zh_cn");
```

### 监听事件
通过 NeoForge 的事件总线监听 `MissingTranslationEvent`：
```java
@SubscribeEvent
public void onMissingTranslation(MissingTranslationEvent event) {
    String key = event.getKey();
    String language = event.getLanguage();
    String modId = event.getModId();
    
    // 在此处执行你的自定义逻辑
}
```
