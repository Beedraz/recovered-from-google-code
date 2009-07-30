/*<license>
Copyright 2007 - $Date$ by PeopleWare n.v.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
</license>*/

package org.ppeew.annotations_I;


import static org.ppeew.annotations_I.License.Type.APACHE_V2;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.MalformedURLException;
import java.net.URL;

import org.ppeew.annotations_I.vcs.SvnInfo;


/**
 * Annotation for license information. By using this annotation,
 * the license information is available in the code.
 *
 * Usage pattern:
 * <pre>
 * ATLicense(APACHE_V2)
 * public class ... {
 *  ...
 * }
 * </pre>
 *
 * @author    Jan Dockx
 */
@Copyright("2007 - $Date$, PeopleWare n.v.")
@License(APACHE_V2)
@SvnInfo(revision = "$Revision$",
         date     = "$Date$")
@Documented
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface License {

  /**
   * Supported license types.
   *
   * @note This list will be extended as necessary.
   */
  public enum Type {
    APACHE_V2    ("http://www.apache.org/licenses/LICENSE-2.0.html"),
    GPL_v2       ("http://www.fsf.org/licensing/licenses/gpl.html"),
    LGPL_v2_1    ("http://www.fsf.org/licensing/licenses/lgpl.html"),
    MPL_v1_1     ("http://www.mozilla.org/MPL/MPL-1.1.html");

    Type(String url) {
      try {
        $url = new URL(url);
      }
      catch (MalformedURLException exc) {
        assert false : "MalformedURLException in definition of Type instance";
      }
    }

    public URL getUrl() {
      return $url;
    }

    private URL $url;

  }

  /**
   * Name of the license.
   *
   * @note This list will be extended as necessary.
   */
  Type value();

}