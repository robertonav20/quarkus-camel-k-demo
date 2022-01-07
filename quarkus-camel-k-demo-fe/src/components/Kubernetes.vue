<template>
  <ui-grid class="grid">
    <ui-grid-cell class="cell" :columns="{default:12}">
      <ui-card>
        <ui-card-content>
          <ui-card-media>
            <ui-card-media-content></ui-card-media-content>
          </ui-card-media>
          <ui-card-text>
            KUBERNETES COMPONENT
          </ui-card-text>
        </ui-card-content>
        <ui-card-actions>
          <ui-card-buttons></ui-card-buttons>
          <ui-card-icons></ui-card-icons>
        </ui-card-actions>
      </ui-card>
    </ui-grid-cell>
  </ui-grid>
</template>

<script>
export default {
  name: 'Kubernetes',
}
</script>
