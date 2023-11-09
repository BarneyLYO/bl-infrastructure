package cn.blinfra.boot.starter.xss;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;

public class JsoupXssCleaner implements XssCleaner {

  private final Safelist safelist;

  private final String baseUri;

  public JsoupXssCleaner() {
    this.safelist = buildSafeList();
    this.baseUri = "";
  }

  private Safelist buildSafeList() {
    Safelist relaxedSafeList = Safelist.relaxed();
    relaxedSafeList.addAttributes(":all", "style", "class");
    relaxedSafeList.addAttributes("a", "target");
    relaxedSafeList.addProtocols("img", "src", "data");
    return relaxedSafeList;
  }

  @Override
  public String clean(String html) {
    return Jsoup.clean(
      html,
      baseUri,
      safelist,
      new Document.OutputSettings().prettyPrint(false)
    );
  }
}
