# Documentación de la Librería `react-native-custom-bottom-tabs`

## Descripción

`react-native-custom-bottom-tabs` permite usar un componente personalizado como bottom tabs en una aplicación React Native. Reemplaza el componente nativo de tabs que usa `react-native-navigation` de Wix con un rootview que contiene tu componente de React Native.

## Requisitos

Para usar esta librería, es necesario tener instalado `react-native-navigation`. Puedes instalarlo siguiendo la [documentación oficial](https://wix.github.io/react-native-navigation/).

## Instalación

1. Asegúrate de tener `react-native-navigation` instalado:
    ```bash
    yarn add react-native-navigation
    ```

2. Instala `react-native-custom-bottom-tabs`:
    ```bash
    yarn add react-native-custom-bottom-tabs
    ```

## Uso

Para usar esta librería, sigue los pasos a continuación:

1. **Configura tus tabs usando `react-native-navigation`**:
    ```javascript
    import { Navigation } from 'react-native-navigation';
    import { setBottomTabsComponent } from 'react-native-custom-bottom-tabs';
    Navigation.registerComponent('CustomTabs', () => CustomTabs);
    Navigation.registerComponent('Screen1', () => Screen1);
    Navigation.registerComponent('Screen2', () => Screen2);

    Navigation.setRoot({
      root: {
        id: 'BOTTOM_TABS',
        bottomTabs: {
          children: [
            {
              stack: {
                children: [
                  {
                    component: {
                      name: 'Screen1'
                    }
                  }
                ],
              }
            },
            {
              stack: {
                children: [
                  {
                    component: {
                      name: 'Screen2'
                    }
                  }
                ],
              }
            }
          ]
        }
      }
    }).then(() => {
        // Este paso debe hacerse después de inicializar los tabs
        setBottomTabsComponent('CustomTabs');
    });
    ```

2. **Crea tu componente personalizado**:
    ```javascript
    import React from 'react';
    import { View, Text, TouchableOpacity } from 'react-native';
    import { Navigation } from 'react-native-navigation';

    const CustomTabs = () => {
      const changeTab = (idxTab) => {
          Navigation.mergeOptions('BOTTOM_TABS', {
            bottomTabs: {
                currentTabIndex: idxTab,
            },
          });
      }
      return (
        <View style={{ flexDirection: 'row', height: 50, justifyContent: 'space-around', alignItems: 'center' }}>
          <TouchableOpacity onPress={() => changeTab(0)} >
            <Text>Tab 1</Text>
          </TouchableOpacity>
          <TouchableOpacity onPress={() => changeTab(1)}>
            <Text>Tab 2</Text>
          </TouchableOpacity>
        </View>
      );
    };

    export default CustomTabs;
    ```

## API

### `setBottomTabsComponent(componentName)`

Reemplaza el componente nativo de tabs con el componente de React Native especificado.

- **Parámetros**:
  - `componentName` (string): El nombre del componente registrado que se usará como el nuevo rootview para los tabs.

- **Ejemplo**:
  ```javascript
  setBottomTabsComponent('CustomTabs');
  ```

## Notas

- Asegúrate de registrar tu componente personalizado con `react-native-navigation` antes de intentar usarlo con `setBottomTabsComponent`.
- Esta librería funciona reemplazando el rootview de los tabs, por lo que debe ser llamada después de la inicialización de los tabs con `react-native-navigation`.

## Contribuir

Si deseas contribuir a este proyecto, por favor abre un pull request o crea un issue en el repositorio de GitHub.

## Licencia

Este proyecto está bajo la licencia MIT. Consulta el archivo `LICENSE` para más detalles.
