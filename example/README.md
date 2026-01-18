# Missing i18n - 示例联动模组

这是一个展示如何与 **Missing i18n** 模组进行联动的示例项目。

## 📖 项目说明

本示例模组展示了：
1. **API 调用**：如何在代码中手动记录缺失的翻译键。
2. **事件监听**：如何通过监听 `MissingTranslationEvent` 事件来捕获并处理其他模组缺失的翻译键。

## 🚀 核心代码

- **主类**：[Missingi18nExample.java](file:///f:/modmc/missingi18n/example/src/main/java/missing/i18n/example/Missingi18nExample.java) - 展示了如何初始化并调用 API。
- **监听器**：[Missingi18nEventListener.java](file:///f:/modmc/missingi18n/example/src/main/java/missing/i18n/example/Missingi18nEventListener.java) - 展示了如何接收实时捕获的缺失键信息。

## 🛠️ 构建说明

1. 确保你已经构建了主模组 `missingi18n` 并获得了其 Jar 包。
2. 将该 Jar 包放入本项目的 `libs/` 文件夹中，并重命名为 `missingi18n.jar`（或者修改 `build.gradle` 中的依赖配置）。
3. 运行 `./gradlew build` 进行编译。

---
本示例由 [aixiaoji](https://github.com/aixiaoji) 提供，基于 `Missing i18n` 模组。
